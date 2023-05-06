package com.epam.esm.dao.implementation;

import com.epam.esm.dao.abstraction.TagDao;
import com.epam.esm.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagDaoImpl implements TagDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Tag findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM certificate_db.tag WHERE id = ?", new TagRowMapper(), id);
    }

    @Override
    public Tag findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM certificate_db.tag WHERE name = ?", new TagRowMapper(), name);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM certificate_db.tag",
                new TagRowMapper());
    }

    @Override
    public Integer insert(Tag obj) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withSchemaName("certificate_db").withTableName("tag").usingColumns("name").usingGeneratedKeyColumns("id").withoutTableColumnMetaDataAccess();
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", obj.getName());
        Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return insertedId.intValue();
    }

    @Override
    public Integer delete(Integer id) {
        return jdbcTemplate.update("DELETE from certificate_db.tag WHERE id = ?", id);
    }

    public static class TagRowMapper implements RowMapper<Tag> {

        public Tag mapRow(ResultSet rs, int row) throws SQLException {
            Tag tag = new Tag();
            tag.setId(rs.getInt("id"));
            tag.setName(rs.getString("name"));
            return tag;
        }
    }
}
