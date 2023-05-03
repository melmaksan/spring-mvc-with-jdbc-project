package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.GiftCertificateService;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import com.epam.esm.service.abstraction.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService giftCertificateService;
    @Autowired
    private TagService tagService;
    @Autowired
    private GiftCertificateToTagService certificateToTagService;

//    @RequestMapping("/")
//    public String showFirstView() {
//        return "first-view";
//    }

    @GetMapping(value = "/certificate/{id}")
    public GiftCertificate getCertificateById(@PathVariable long id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @GetMapping(value = "/certificates/findByName/{name}")
    public GiftCertificate getGiftCertificatesByName(@PathVariable String name) {
        return giftCertificateService.getGiftCertificateByName(name);
    }

    @GetMapping(value = "/certificates/findByPart/{part}")
    public List<GiftCertificate> getGiftCertificatesByPart(@PathVariable String part) {
        return giftCertificateService.getGiftCertificatesByPart(part);
    }

    @GetMapping(value = "/certificates/findByTag/{name}")
    public List<GiftCertificate> getGiftCertificatesByTag(@PathVariable String name) {
        Tag tag = tagService.getTagByName(name);
        return getCertificatesByTag(tag.getId());
    }

    private List<GiftCertificate> getCertificatesByTag(int id) {
        List<GiftCertificate> certificates = new ArrayList<>();
        List<GiftCertificateToTag> certificateToTags = certificateToTagService.findAllCertificatesByTagId(id);
        for (GiftCertificateToTag certificateToTag : certificateToTags) {
            certificates.add(giftCertificateService.getGiftCertificateById(certificateToTag.getGiftCertificateId()));
        }
        return certificates;
    }

    @GetMapping("/certificates")
    public List<GiftCertificate> getAllCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping(value = "/certificates/ascDate")
    public List<GiftCertificate> getCertificatesAscDate() {
        return giftCertificateService.sortGiftCertificatesByDateAsc();
    }

    @GetMapping(value = "/certificates/descDate")
    public List<GiftCertificate> getCertificatesDescDate() {
        return giftCertificateService.sortGiftCertificatesByDateDesc();
    }

    @GetMapping(value = "/certificates/ascName")
    public List<GiftCertificate> getCertificatesAscName() {
        return giftCertificateService.sortGiftCertificatesByNameAsc();
    }

    @GetMapping(value = "/certificates/descName")
    public List<GiftCertificate> getCertificatesDescName() {
        return giftCertificateService.sortGiftCertificatesByNameDesc();
    }


    @DeleteMapping(value = "/certificates/{id}")
    public String deleteCertificate(@PathVariable long id) {
        long res = giftCertificateService.deleteGiftCertificate(id);
        if (res > 0) {
            return "Certificate with id = " + id + " was deleted";
        } else {
            return "Something wrong, please try again";
        }
    }

    @PostMapping(value = "/certificates")
    public GiftCertificate addCertificate(@RequestBody GiftCertificate giftCertificate) {
        long id = giftCertificateService.addGiftCertificate(giftCertificate);
        return giftCertificateService.getGiftCertificateById(id);
    }

    @PutMapping(value = "/certificates")
    public GiftCertificate updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
        return giftCertificateService.getGiftCertificateById(giftCertificate.getId());
    }
}
