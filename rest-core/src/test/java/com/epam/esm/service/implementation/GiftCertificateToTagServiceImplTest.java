package com.epam.esm.service.implementation;

import com.epam.esm.dao.abstraction.GiftCertificateToTagDao;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateToTagServiceImplTest {

    @Mock
    private static GiftCertificateToTagDao toTagDao;
    @InjectMocks
    private static GiftCertificateToTagService toTagService;
    private static GiftCertificateToTag toTag;


    @BeforeAll
    static void setUp() {
        toTagService = new GiftCertificateToTagServiceImpl();

        toTag = new GiftCertificateToTag();
        toTag.setTagId(1);
        toTag.setGiftCertificateId(1);
    }

    @Test
    void findAllTagsByCertificateId() {
        List<GiftCertificateToTag> toTagList = getToTagList();
        reformToTagList(toTagList);

        when(toTagDao.findByCertificateId(toTag.getGiftCertificateId())).thenReturn(toTagList);

        List<GiftCertificateToTag> foundList = toTagService.findAllTagsByCertificateId(toTag.getGiftCertificateId());
        assertEquals(toTagList, foundList);
    }

    private void reformToTagList(List<GiftCertificateToTag> toTagList) {
        toTagList.remove(toTag);
        GiftCertificateToTag giftCertificateToTag = new GiftCertificateToTag();
        giftCertificateToTag.setGiftCertificateId(2);
        giftCertificateToTag.setTagId(2);
        toTagList.add(giftCertificateToTag);
    }

    private List<GiftCertificateToTag> getToTagList() {
        List<GiftCertificateToTag> toTagList = new ArrayList<>();
        GiftCertificateToTag certificateToTag = new GiftCertificateToTag();
        certificateToTag.setGiftCertificateId(2);
        certificateToTag.setTagId(1);

        toTagList.add(toTag);
        toTagList.add(certificateToTag);
        return toTagList;
    }

    @Test
    void findAllCertificatesByTagId() {
        List<GiftCertificateToTag> toTagList = getToTagList();

        when(toTagDao.findByTagId(toTag.getTagId())).thenReturn(toTagList);

        List<GiftCertificateToTag> foundList = toTagService.findAllCertificatesByTagId(toTag.getTagId());
        assertEquals(toTagList, foundList);
    }

    @Test
    void getAllCertificatesToTag() {
        List<GiftCertificateToTag> toTagList = getToTagList();
        when(toTagDao.findAll()).thenReturn(toTagList);

        List<GiftCertificateToTag> foundList = toTagService.getAllCertificatesToTag();
        assertEquals(toTagList, foundList);
    }

    @Test
    void addGiftCertificate() {
        when(toTagDao.insert(toTag)).thenReturn(1L);

        long result = toTagService.addGiftCertificate(toTag);
        assertEquals(1, result);
    }

    @Test
    void deleteGiftCertificate() {
        when(toTagDao.delete(toTag.getGiftCertificateId())).thenReturn(1L);

        long del = toTagService.deleteGiftCertificate(toTag.getGiftCertificateId());
        assertEquals(1, del);
    }
}