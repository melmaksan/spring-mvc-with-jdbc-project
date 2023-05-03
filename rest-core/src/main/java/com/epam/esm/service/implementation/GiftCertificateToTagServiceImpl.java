package com.epam.esm.service.implementation;

import com.epam.esm.dao.abstraction.GiftCertificateToTagDao;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
