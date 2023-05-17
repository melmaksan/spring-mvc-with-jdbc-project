package com.epam.esm.dao;

import com.epam.esm.entities.GiftCertificateToTag;

import java.util.List;

public interface GiftCertificateToTagDao extends GenericDao<GiftCertificateToTag, Long> {

    List<GiftCertificateToTag> findByCertificateId(Long id);

    List<GiftCertificateToTag> findByTagId(int id);
}
