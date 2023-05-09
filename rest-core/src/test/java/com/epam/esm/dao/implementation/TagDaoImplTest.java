package com.epam.esm.dao.implementation;

import com.epam.esm.dao.abstraction.TagDao;
import com.epam.esm.entities.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
class TagDaoImplTest {

    private static TagDao tagDao;
    private static Tag tag;

    @BeforeAll
    static void setUp() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("testCoreApplicationContext.xml");

        tagDao = context.getBean(TagDaoImpl.class);

        tag = new Tag();
        tag.setName("test");
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDao.findAll();

        assertNotNull(tagList);
        assertEquals(3, tagList.size(), "there are two default tags");
    }

    @Test
    void insert() {
        int id = tagDao.insert(tag);
        List<Tag> tagList = tagDao.findAll();

//        assertEquals(9, id);
        assertEquals(3, tagList.size(), "there are 3 tags after insert");
    }

    @Test
    void findById() {
        Tag tag2 = tagDao.findById(1);

        assertNotNull(tag2);
        assertEquals("discount", tag2.getName());
    }

    @Test
    void findByName() {
        Tag tag3 = tagDao.findByName("gift");

        assertNotNull(tag3);
        assertEquals(32, tag3.getId());
    }


    @Test
    void delete() {
        Tag tag4 = tagDao.findByName("test");
        int id = tagDao.delete(tag4.getId());
        List<Tag> tagList = tagDao.findAll();

        assertEquals(1, id);
        assertEquals(2, tagList.size(), "there are 3 tags after insert");
    }
}