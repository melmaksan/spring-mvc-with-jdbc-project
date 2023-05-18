package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.GiftCertificateToTag;

import java.util.List;

public interface GiftCertificateToTagDao extends GenericDao<GiftCertificateToTag, Long> {

    List<GiftCertificateToTag> findByCertificateId(Long id);

    List<GiftCertificateToTag> findByTagId(int id);
}
