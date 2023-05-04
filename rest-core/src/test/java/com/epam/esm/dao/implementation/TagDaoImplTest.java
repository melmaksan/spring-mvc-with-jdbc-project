package com.epam.esm.dao.implementation;

import com.epam.esm.entities.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {

    private static TagDaoImpl tagDao;

    @BeforeAll
    static void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("h2/certificateSchema.sql")
                .addScript("h2/certificateData.sql")
                .build();
        tagDao = new TagDaoImpl();
        tagDao.setDataSource(db);
        tagDao.postConstruct();
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDao.findAll();

        assertNotNull(tagList);
//        assertEquals(2, tagList.size(), "there are two default tags");
    }

    @Test
    void insert() {
    }

    @Test
    void findById() {

    }

    @Test
    void findByName() {
    }


    @Test
    void delete() {
    }
}