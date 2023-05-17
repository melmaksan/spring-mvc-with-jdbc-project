package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entities.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificate findById(long id) throws DataAccessException {
        return jdbcTemplate.queryForObject
                ("SELECT * FROM certificate_db.gift_certificate WHERE id = ?", new GiftCertificateRowMapper(), id);
    }

    @Override
    public GiftCertificate findByName(String name) {
        return jdbcTemplate.queryForObject
                ("SELECT * FROM certificate_db.gift_certificate WHERE name = ?", new GiftCertificateRowMapper(), name);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate",
                new GiftCertificateRowMapper());
    }

    @Override
    public Long insert(GiftCertificate obj) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withSchemaName("certificate_db").withTableName("gift_certificate")
                .usingColumns("name", "description", "price", "duration", "create_date", "last_update_date")
                .usingGeneratedKeyColumns("id").withoutTableColumnMetaDataAccess();
        Map<String, Object> parameters = new HashMap<>(6);
        parameters.put("name", obj.getName());
        parameters.put("description", obj.getDescription());
        parameters.put("price", obj.getPrice());
        parameters.put("duration", obj.getDuration());
        parameters.put("create_date", obj.getCreateDate());
        parameters.put("last_update_date", obj.getCreateDate());
        Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return insertedId.longValue();
    }

    @Override
    public long update(GiftCertificate certificate) {
        List<Object> fields = new ArrayList<>();
        String parameters = fieldsCheck(certificate, fields);
        if (fields.size() > 0 && parameters.length() > 0) {
            fields.add(certificate.getLastUpdateDate());
            fields.add(certificate.getId());
            String sql = "UPDATE certificate_db.gift_certificate SET " + parameters + " = ?, last_update_date = ? WHERE id = ?";
            return jdbcTemplate.update(sql, fields.toArray());
        } else {
            return 0;
        }

    }

    private String fieldsCheck(GiftCertificate certificate, List<Object> fields) {
        StringJoiner res = new StringJoiner(" = ?, ");
        if (certificate.getName() != null) {
            res.add("name");
            fields.add(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            res.add("description");
            fields.add(certificate.getDescription());
        }
        if (certificate.getPrice() != 0) {
            res.add("price");
            fields.add(certificate.getPrice());
        }
        if (certificate.getDuration() != 0) {
            res.add("duration");
            fields.add(certificate.getDuration());
        }
        return res.toString();
    }

    @Override
    public List<GiftCertificate> findByPart(String part) {
        String wildcard = "%" + part + "%";
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate WHERE name LIKE ? OR description LIKE ?",
                new GiftCertificateRowMapper(), wildcard, wildcard);
    }

    @Override
    public List<GiftCertificate> ascByDate() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate ORDER BY create_date ASC",
                new GiftCertificateRowMapper());
    }

    @Override
    public List<GiftCertificate> descByDate() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate ORDER BY create_date DESC",
                new GiftCertificateRowMapper());
    }

    @Override
    public List<GiftCertificate> ascByName() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate ORDER BY name ASC",
                new GiftCertificateRowMapper());
    }

    @Override
    public List<GiftCertificate> descByName() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate ORDER BY name DESC",
                new GiftCertificateRowMapper());
    }

    @Override
    public Long delete(Long id) {
        return (long) jdbcTemplate.update("DELETE from certificate_db.gift_certificate WHERE id = ?", id);
    }

    public static class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

        public GiftCertificate mapRow(ResultSet rs, int row) throws SQLException {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getInt("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getInt("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(convertTime(rs.getTimestamp("create_date")));
            giftCertificate.setLastUpdateDate(convertTime(rs.getTimestamp("last_update_date")));
            return giftCertificate;
        }

        private LocalDateTime convertTime(Timestamp timestamp) {
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            ZonedDateTime zonedUTC = localDateTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("GMT-3"));
            return zonedIST.toLocalDateTime();
        }
    }
}
