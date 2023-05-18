package com.epam.esm.certificate_service.dao.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateToTagDao;
import com.epam.esm.certificate_service.entities.GiftCertificateToTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GiftCertificateToTagDaoImpl implements GiftCertificateToTagDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificateToTag> findByCertificateId(Long id) {
        return jdbcTemplate.query
                ("SELECT * FROM certificate_db.gift_certificate_has_tag WHERE gift_certificate_id = ?",
                        new GiftCertificateToTagRowMapper(), id);
    }

    @Override
    public List<GiftCertificateToTag> findByTagId(int id) {
        return jdbcTemplate.query
                ("SELECT * FROM certificate_db.gift_certificate_has_tag WHERE tag_id = ?",
                        new GiftCertificateToTagRowMapper(), id);
    }

    @Override
    public List<GiftCertificateToTag> findAll() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.gift_certificate_has_tag",
                new GiftCertificateToTagRowMapper());
    }


    @Override
    public Long insert(GiftCertificateToTag obj) {
        String sql = "INSERT INTO certificate_db.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?, ?)";
        return (long) jdbcTemplate.update(sql, obj.getGiftCertificateId(), obj.getTagId());
    }

    @Override
    public Long delete(Long id) {
        return (long) jdbcTemplate.update("DELETE from certificate_db.gift_certificate_has_tag WHERE gift_certificate_id = ?", id);
    }

    public static class GiftCertificateToTagRowMapper implements RowMapper<GiftCertificateToTag> {

        public GiftCertificateToTag mapRow(ResultSet rs, int row) throws SQLException {
            GiftCertificateToTag certificateToTag = new GiftCertificateToTag();
            certificateToTag.setTagId(rs.getInt("tag_id"));
            certificateToTag.setGiftCertificateId(rs.getInt("gift_certificate_id"));
            return certificateToTag;
        }
    }
}
