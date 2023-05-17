package com.epam.esm.service;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate getGiftCertificateById(long id);

    GiftCertificate getGiftCertificateByName(String name);

    List<GiftCertificate> getAllGiftCertificates();

    long addGiftCertificate(GiftCertificate userDetail);

    long updateGiftCertificate(GiftCertificate userDetail);

    long deleteGiftCertificate(long id);

    List<GiftCertificate> getGiftCertificatesByPart (String part);

    List<GiftCertificate> sortGiftCertificatesByDateAsc();

    List<GiftCertificate> sortGiftCertificatesByDateDesc();

    List<GiftCertificate> sortGiftCertificatesByNameAsc();

    List<GiftCertificate> sortGiftCertificatesByNameDesc();

    List<Tag> getTags(long id);

}
