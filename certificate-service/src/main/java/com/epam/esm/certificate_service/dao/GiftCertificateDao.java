package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends GenericDao<GiftCertificate, Long> {

    GiftCertificate findById(long id);

    GiftCertificate findByName(String name);

    long update(GiftCertificate certificate);

    List<GiftCertificate> findByPart(String part);

    List<GiftCertificate> ascByDate();

    List<GiftCertificate> descByDate();

    List<GiftCertificate> ascByName();

    List<GiftCertificate> descByName();
}
