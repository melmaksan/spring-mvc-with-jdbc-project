package com.epam.esm.certificate_service.dao.implementation;

import com.epam.esm.certificate_service.dao.GiftCertificateToTagDao;
import com.epam.esm.certificate_service.entities.GiftCertificateToTag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/testCoreApplicationContext.xml"})
class GiftCertificateToTagDaoImplTest {

    @Autowired
    private GiftCertificateToTagDao certificateToTagDao;
    private static GiftCertificateToTag certificateToTag;


    @BeforeAll
    static void setUp() {
        certificateToTag = new GiftCertificateToTag();

        certificateToTag.setGiftCertificateId(3);
        certificateToTag.setTagId(1);
    }

    @Test
    void findByCertificateId() {
        List<GiftCertificateToTag> toCertificateList = certificateToTagDao.findByCertificateId(1L);

        assertNotNull(toCertificateList);
        assertEquals(2, toCertificateList.size());
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
        List<GiftCertificateToTag> giftCertificateToTagList = certificateToTagDao.findByCertificateId(certificateToTag.getGiftCertificateId());
        for (GiftCertificateToTag toTag : giftCertificateToTagList) {
            long id = certificateToTagDao.delete(toTag.getGiftCertificateId());

            List<GiftCertificateToTag> tagList = certificateToTagDao.findAll();

            assertEquals(1, id);
            assertEquals(4, tagList.size(), "there are 3 tags after insert");
        }
    }
}