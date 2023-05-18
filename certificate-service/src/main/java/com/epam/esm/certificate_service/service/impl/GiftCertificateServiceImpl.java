package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.GiftCertificateDao;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.entities.GiftCertificateToTag;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.GiftCertificateToTagService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.exeption_handling.exeptions.DuplicateDataException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String CODE = "01";

    @Autowired
    private GiftCertificateDao giftCertificateDao;
    @Autowired
    private TagService tagService;
    @Autowired
    private GiftCertificateToTagService certificateToTagService;

    @Override
    public GiftCertificate getGiftCertificateById(long id) {
        try {
            GiftCertificate certificate = giftCertificateDao.findById(id);
            List<Tag> tagList = getTags(id);
            certificate.setTags(tagList);
            return certificate;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("There is no certificate with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    public GiftCertificate getGiftCertificateByName(String name) {
        try {
            GiftCertificate certificate = giftCertificateDao.findByName(name);
            List<Tag> tagList = getTags(certificate.getId());
            certificate.setTags(tagList);
            return certificate;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("There is no certificate with name '" + name + "' in DB", CODE);
        }
    }

    @Override
    public List<Tag> getTags(long id) {
        List<Tag> tags = new ArrayList<>();
        List<GiftCertificateToTag> certificateToTags = certificateToTagService.findAllTagsByCertificateId(id);
        for (GiftCertificateToTag certificateToTag : certificateToTags) {
            tags.add(tagService.getTagById(certificateToTag.getTagId()));
        }
        return tags;
    }

    @Transactional
    @Override
    public long addGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime.now());
        if (giftCertificate.getName() == null || giftCertificate.getDescription() == null ||
                giftCertificate.getCreateDate() == null) {
            throw new EmptyRequestBodyException("Fields name, description and createDate " +
                    "are required, please try again!", CODE);
        }
        long id = giftCertificateDao.insert(giftCertificate);
        if (id > 0 && giftCertificate.getTags() != null) {
            tagsUpdate(giftCertificate, id);
        }
        return id;
    }

    @Transactional
    @Override
    public long updateGiftCertificate(GiftCertificate giftCertificate) {
        try {
            giftCertificateDao.findById(giftCertificate.getId());
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            long id = giftCertificateDao.update(giftCertificate);
            if (id != 0) {
                if (giftCertificate.getTags() != null) {
                    tagsUpdate(giftCertificate, giftCertificate.getId());
                }
                return id;
            } else {
                throw new EmptyRequestBodyException("Please, fill the request body and try again", CODE);
            }
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("Can't update certificate with id '" + giftCertificate.getId() +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    private void tagsUpdate(GiftCertificate giftCertificate, long id) {
        List<Tag> tagList = giftCertificate.getTags();
        if (!tagList.isEmpty()) {
            List<Tag> allTags = tagService.getAllTags();
            for (Tag tag : tagList) {
                Optional<Tag> optionalTag = allTags.stream()
                        .filter(s -> (tag.getName()).equals(s.getName()))
                        .findAny();
                if (optionalTag.isPresent()) {
                    addCertificateToTag(id, optionalTag.get().getId());
                } else {
                    int tagId = tagService.addTag(tag);
                    addCertificateToTag(id, tagId);
                }
            }
        }
    }

    private void addCertificateToTag(long certificateId, int tagId) {
        GiftCertificateToTag certificateToTag = new GiftCertificateToTag();
        certificateToTag.setTagId(tagId);
        certificateToTag.setGiftCertificateId(certificateId);
        try {
            certificateToTagService.addGiftCertificate(certificateToTag);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateDataException("Can't update certificate with id '" + certificateId +
                    "' because it already has tag named '" + tagService.getTagById(tagId).getName() + "'", CODE);
        }

    }

    @Transactional
    @Override
    public long deleteGiftCertificate(long id) {
        try {
            GiftCertificate certificate = giftCertificateDao.findById(id);
            return giftCertificateDao.delete(certificate.getId());
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("Can't delete certificate with id '" + id +
                    "' because it doesn't exist in DB", CODE);
        }
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateDao.findAll();
        addTags(certificates);
        return certificates;
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByPart(String part) {
        List<GiftCertificate> certificates = giftCertificateDao.findByPart(part);
        addTags(certificates);
        return certificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateAsc() {
        List<GiftCertificate> certificates = giftCertificateDao.ascByDate();
        addTags(certificates);
        return certificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByDateDesc() {
        List<GiftCertificate> certificates = giftCertificateDao.descByDate();
        addTags(certificates);
        return certificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameAsc() {
        List<GiftCertificate> certificates = giftCertificateDao.ascByName();
        addTags(certificates);
        return certificates;
    }

    @Override
    public List<GiftCertificate> sortGiftCertificatesByNameDesc() {
        List<GiftCertificate> certificates = giftCertificateDao.descByName();
        addTags(certificates);
        return certificates;
    }

    private void addTags(List<GiftCertificate> certificates) {
        for (GiftCertificate certificate : certificates) {
            List<Tag> tagList = getTags(certificate.getId());
            certificate.setTags(tagList);
        }
    }

}
