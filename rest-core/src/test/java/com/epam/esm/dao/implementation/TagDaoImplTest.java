package com.epam.esm.dao.implementation;

import com.epam.esm.entities.Tag;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class TagDaoImplTest {

    private static TagDaoImpl tagDao;
    private static Tag tag;

    @BeforeAll
    static void setUp() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("testCoreApplicationContext.xml");
        BasicDataSource dataSource = context.getBean(BasicDataSource.class);

        tagDao = new TagDaoImpl();
        tagDao.setDataSource(dataSource);
        tagDao.postConstruct();

        tag = new Tag();
        tag.setName("test");
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDao.findAll();

        assertNotNull(tagList);
        assertEquals(2, tagList.size(), "there are two default tags");
    }

    @Test
    void insert() {
        int id = tagDao.insert(tag);
        List<Tag> tagList = tagDao.findAll();

        assertEquals(3, id);
        assertEquals(3, tagList.size(), "there are 3 tags after insert");
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