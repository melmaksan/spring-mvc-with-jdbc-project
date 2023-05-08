package com.epam.esm.service.implementation;

import com.epam.esm.dao.abstraction.GiftCertificateDao;
import com.epam.esm.dao.abstraction.TagDao;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.GiftCertificateService;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import com.epam.esm.service.abstraction.TagService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private GiftCertificateToTagService certificateToTagService;
    @InjectMocks
    private static GiftCertificateService certificateService;
    private static GiftCertificate certificate;


    @BeforeAll
    static void setUp() {
        certificateService = new GiftCertificateServiceImpl();

        certificate = new GiftCertificate();
        certificate.setId(1);
        certificate.setName("certificate");
        certificate.setDescription("test");
        certificate.setPrice(100);
        certificate.setDuration(10);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void getGiftCertificateById() {
        when(giftCertificateDao.findById(certificate.getId())).thenReturn(certificate);

        GiftCertificate foundGiftCertificate = certificateService.getGiftCertificateById(certificate.getId());
        assertEquals(certificate, foundGiftCertificate);
    }

    @Test
    void getGiftCertificateByName() {
        when(giftCertificateDao.findByName(certificate.getName())).thenReturn(certificate);

        GiftCertificate foundGiftCertificate = certificateService.getGiftCertificateByName(certificate.getName());
        assertEquals(certificate, foundGiftCertificate);
    }

    @Test
    void getGiftCertificatesByPart() {
        List<GiftCertificate> list = getCertificateList();

        when(giftCertificateDao.findByPart(certificate.getName().substring(0, 3))).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.getGiftCertificatesByPart(certificate.getName().substring(0, 3));
        assertEquals(list, foundList);
    }

    @Test
    void addGiftCertificate() {
        when(giftCertificateDao.insert(certificate)).thenReturn(10L);

        long result = certificateService.addGiftCertificate(certificate);
        assertEquals(10, result);
    }

    @Test
    void updateGiftCertificate() {
        when(giftCertificateDao.findById(certificate.getId())).thenReturn(certificate);
        when(giftCertificateDao.update(certificate)).thenReturn(1L);

        long result = certificateService.updateGiftCertificate(certificate);
        assertEquals(1, result);
    }

    @Test
    void deleteGiftCertificate() {
        when(giftCertificateDao.findById(certificate.getId())).thenReturn(certificate);
        when(giftCertificateDao.delete(certificate.getId())).thenReturn(1L);

        long del = certificateService.deleteGiftCertificate(certificate.getId());
        assertEquals(1, del);
    }

    @Test
    void getAllGiftCertificates() {
        List<GiftCertificate> list = getCertificateList();

        when(giftCertificateDao.findAll()).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.getAllGiftCertificates();
        assertEquals(list, foundList);
    }

    private List<GiftCertificate> getCertificateList() {
        List<GiftCertificate> list = new ArrayList<>();
        GiftCertificate secondCertificate = new GiftCertificate();
        secondCertificate.setId(2);
        secondCertificate.setName("second");
        secondCertificate.setDescription("test");
        secondCertificate.setPrice(200);
        secondCertificate.setDuration(20);
        secondCertificate.setCreateDate(LocalDateTime.now());
        secondCertificate.setLastUpdateDate(LocalDateTime.now());

        list.add(certificate);
        list.add(secondCertificate);
        return list;
    }


    @Test
    void sortGiftCertificatesByDateAsc() {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate)).collect(Collectors.toList());

        System.out.println(list);
        when(giftCertificateDao.ascByDate()).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.sortGiftCertificatesByDateAsc();
        assertEquals(list, foundList);
    }

    @Test
    void sortGiftCertificatesByDateDesc() {
        List<GiftCertificate> list = getCertificateList()
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate).reversed()).collect(Collectors.toList());

        when(giftCertificateDao.descByDate()).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.sortGiftCertificatesByDateDesc();
        assertEquals(list, foundList);
    }

    @Test
    void sortGiftCertificatesByNameAsc() {
        List<GiftCertificate> list = getCertificateList().stream()
                .sorted(Comparator.comparing(GiftCertificate::getName)).collect(Collectors.toList());

        when(giftCertificateDao.ascByName()).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.sortGiftCertificatesByNameAsc();
        assertEquals(list, foundList);
    }

    @Test
    void sortGiftCertificatesByNameDesc() {
        List<GiftCertificate> list = getCertificateList().stream()
                .sorted(Comparator.comparing(GiftCertificate::getName).reversed()).collect(Collectors.toList());

        when(giftCertificateDao.descByName()).thenReturn(list);

        List<GiftCertificate> foundList = certificateService.sortGiftCertificatesByNameDesc();
        assertEquals(list, foundList);
    }
}