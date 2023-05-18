package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateToTagDao;
import com.epam.esm.certificate_service.entities.GiftCertificateToTag;
import com.epam.esm.certificate_service.service.GiftCertificateToTagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GiftCertificateToTagServiceImpl implements GiftCertificateToTagService {

    @Autowired
    private GiftCertificateToTagDao certificateToTagDao;

    @Override
    public List<GiftCertificateToTag> findAllTagsByCertificateId(long id) {
        return certificateToTagDao.findByCertificateId(id);
    }

    @Override
    public List<GiftCertificateToTag> findAllCertificatesByTagId(int id) {
        return certificateToTagDao.findByTagId(id);
    }

    @Override
    public List<GiftCertificateToTag> getAllCertificatesToTag() {
        return certificateToTagDao.findAll();
    }

    @Override
    public long addGiftCertificate(GiftCertificateToTag certificateToTag) {
        return certificateToTagDao.insert(certificateToTag);
    }

    @Override
    public long deleteGiftCertificate(long id) {
        return certificateToTagDao.delete(id);
    }
}
