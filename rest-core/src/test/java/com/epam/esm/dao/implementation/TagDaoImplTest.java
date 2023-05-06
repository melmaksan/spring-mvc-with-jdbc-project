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
        System.out.println(tagList);

        assertNotNull(tagList);
        assertEquals(3, tagList.size(), "there are two default tags");
    }

    @Test
    void insert() {
        int id = tagDao.insert(tag);
        List<Tag> tagList = tagDao.findAll();
        System.out.println(tagList);

//        assertEquals(9, id);
        assertEquals(5, tagList.size(), "there are 3 tags after insert");
    }

    @Test
    void findById() {
        Tag tag2 = tagDao.findById(1);
        System.out.println(tag2);

        assertNotNull(tag2);
        assertEquals("discount", tag2.getName());
    }

    @Test
    void findByName() {
        Tag tag3 = tagDao.findByName("gift");
        System.out.println(tag3);

        assertNotNull(tag3);
        assertEquals(2, tag3.getId());
    }


    @Test
    void delete() {
        int id = tagDao.delete(24);
        List<Tag> tagList = tagDao.findAll();

        assertEquals(1, id);
        assertEquals(2, tagList.size(), "there are 3 tags after insert");
    }
}