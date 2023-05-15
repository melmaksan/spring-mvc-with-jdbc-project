package com.epam.esm.controllers;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.Mapper;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.GiftCertificateToTag;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.GiftCertificateService;
import com.epam.esm.service.abstraction.GiftCertificateToTagService;
import com.epam.esm.service.abstraction.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final GiftCertificateToTagService certificateToTagService;
    private final Mapper mapper;

    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService,
                                     GiftCertificateToTagService certificateToTagService, Mapper mapper) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
        this.certificateToTagService = certificateToTagService;
        this.mapper = mapper;
    }


//    @RequestMapping("/")
//    public String showFirstView() {
//        return "first-view";
//    }

    @GetMapping(value = "/certificate/{id}")
    public CertificateDTO getCertificateById(@PathVariable long id) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateById(id));
    }

    @GetMapping(value = "/certificates/findByName/{name}")
    public CertificateDTO getGiftCertificatesByName(@PathVariable String name) {
        return mapper.toCertificateDto(giftCertificateService.getGiftCertificateByName(name));
    }

    @GetMapping(value = "/certificates/findByPart/{part}")
    public List<CertificateDTO> getGiftCertificatesByPart(@PathVariable String part) {
        return giftCertificateService.getGiftCertificatesByPart(part)
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/findByTag/{name}")
    public List<CertificateDTO> getGiftCertificatesByTag(@PathVariable String name) {
        Tag tag = tagService.getTagByName(name);
        return getCertificatesByTag(tag.getId()).stream().map(mapper::toCertificateDto).collect(Collectors.toList());
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
    public List<CertificateDTO> getAllCertificates() {
        return giftCertificateService.getAllGiftCertificates()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/ascDate")
    public List<CertificateDTO> getCertificatesAscDate() {
        return giftCertificateService.sortGiftCertificatesByDateAsc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/descDate")
    public List<CertificateDTO> getCertificatesDescDate() {
        return giftCertificateService.sortGiftCertificatesByDateDesc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/ascName")
    public List<CertificateDTO> getCertificatesAscName() {
        return giftCertificateService.sortGiftCertificatesByNameAsc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/certificates/descName")
    public List<CertificateDTO> getCertificatesDescName() {
        return giftCertificateService.sortGiftCertificatesByNameDesc()
                .stream().map(mapper::toCertificateDto).collect(Collectors.toList());
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
    public CertificateDTO addCertificate(@RequestBody GiftCertificate giftCertificate) {
        long id = giftCertificateService.addGiftCertificate(giftCertificate);
        return getCertificateById(id);
    }

    @PutMapping(value = "/certificates")
    public CertificateDTO updateCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
        return getCertificateById(giftCertificate.getId());
    }
}
