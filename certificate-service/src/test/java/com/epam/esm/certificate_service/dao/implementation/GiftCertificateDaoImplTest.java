package com.epam.esm.certificate_service.dao.implementation;

import com.epam.esm.certificate_service.dao.GiftCertificateDao;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/testCoreApplicationContext.xml"})
@Transactional
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;
    private static GiftCertificate certificate;

    @BeforeAll
    static void setUp() {
        certificate = new GiftCertificate();

        certificate.setName("test");
        certificate.setDescription("testing");
        certificate.setPrice(100);
        certificate.setDuration(10);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void findById() {
        GiftCertificate certificate2 = giftCertificateDao.findById(1);

        assertNotNull(certificate2);
        assertEquals("Promo_1000", certificate2.getName());
    }

    @Test
    void findByName() {
        GiftCertificate certificate3 = giftCertificateDao.findByName("Promo_100");

        assertNotNull(certificate3);
        assertEquals(2, certificate3.getId());
    }

    @Test
    void findByPart() {
        List<GiftCertificate> certificateList = giftCertificateDao.findByPart(certificate.getName().substring(0, 3));

        for (GiftCertificate giftCertificate : certificateList) {
            assertNotNull(giftCertificate);
            assertEquals("update", giftCertificate.getName());
        }
    }

    @Test
    void findAll() {
        List<GiftCertificate> certificateList = giftCertificateDao.findAll();

        assertNotNull(certificateList);
        assertEquals(3, certificateList.size(), "there are 3 default tags");
    }

    @Test
    void insert() {
        long id = giftCertificateDao.insert(certificate);
        List<GiftCertificate> list = giftCertificateDao.findAll();
        GiftCertificate certificate3 = giftCertificateDao.findById(id);


        assertEquals("test", certificate3.getName());
        assertEquals(4, list.size(), "there are 4 tags after insert");
    }

    @Test
    void update() {
        GiftCertificate certificate4 = giftCertificateDao.findById(3);

        certificate4.setName("update");
        certificate4.setDuration(200);
        certificate4.setLastUpdateDate(LocalDateTime.now());

        long res = giftCertificateDao.update(certificate4);
        GiftCertificate certificate5 = giftCertificateDao.findById(3);

        assertEquals(1, res);
        assertEquals("update", certificate5.getName());
        assertEquals(200, certificate5.getDuration());

    }

    @Test
    void ascByDate() {
        List<GiftCertificate> certificates = giftCertificateDao.ascByDate();

        assertNotNull(certificates);
        assertEquals("Promo_1000", certificates.get(0).getName());
    }

    @Test
    void descByDate() {
        List<GiftCertificate> certificates = giftCertificateDao.ascByName();

        assertNotNull(certificates);
        assertEquals("Promo_100", certificates.get(0).getName());
    }

    @Test
    void ascByName() {
        List<GiftCertificate> certificates = giftCertificateDao.ascByName();

        assertNotNull(certificates);
        assertEquals("Promo_100", certificates.get(0).getName());
    }

    @Test
    void descByName() {
        List<GiftCertificate> certificates = giftCertificateDao.descByName();

        assertNotNull(certificates);
        assertEquals("Promo_500", certificates.get(0).getName());
    }

    @Test
    void delete() {
        long res = giftCertificateDao.delete(3L);
        List<GiftCertificate> certificates = giftCertificateDao.findAll();

        assertEquals(1, res);
        assertEquals(2, certificates.size(), "there are 3 tags after insert");
    }
}