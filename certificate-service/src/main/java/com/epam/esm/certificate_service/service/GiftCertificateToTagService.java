package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.GiftCertificateToTag;

import java.util.List;

public interface GiftCertificateToTagService {

    List<GiftCertificateToTag> findAllTagsByCertificateId(long id);

    List<GiftCertificateToTag> findAllCertificatesByTagId(int id);

    List<GiftCertificateToTag> getAllCertificatesToTag();

    long addGiftCertificate(GiftCertificateToTag certificateToTag);

    long deleteGiftCertificate(long id);

}
