package com.epam.esm.dao.implementation;

import com.epam.esm.dao.abstraction.GiftCertificateToTagDao;
import com.epam.esm.entities.GiftCertificateToTag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateToTagDaoImplTest {

    private static GiftCertificateToTagDao certificateToTagDao;
    private static GiftCertificateToTag certificateToTag;


    @BeforeAll
    static void setUp() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("testCoreApplicationContext.xml");

        certificateToTagDao = context.getBean(GiftCertificateToTagDaoImpl.class);

        certificateToTag = new GiftCertificateToTag();
        certificateToTag.setGiftCertificateId(3);
        certificateToTag.setTagId(1);
    }

    @Test
    void findByCertificateId() {
        List<GiftCertificateToTag> toCertificateList = certificateToTagDao.findByCertificateId(1L);

        assertNotNull(toCertificateList);
    }

    @Test
    void findByTagId() {
        List<GiftCertificateToTag> toTagList = certificateToTagDao.findByTagId(2);

        assertNotNull(toTagList);
    }

    @Test
    void findAll() {
        List<GiftCertificateToTag> list = certificateToTagDao.findAll();

        assertNotNull(list);
    }

    @Test
    void insert() {
        long id = certificateToTagDao.insert(certificateToTag);

        List<GiftCertificateToTag> tagList = certificateToTagDao.findAll();

        assertEquals(1, id);
        assertEquals(5, tagList.size());

    }

    @Test
    void delete() {
        long id = certificateToTagDao.delete(3L);
        List<GiftCertificateToTag> tagList = certificateToTagDao.findAll();

        assertEquals(1, id);
        assertEquals(4, tagList.size(), "there are 3 tags after insert");
    }
}