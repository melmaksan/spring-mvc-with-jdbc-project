package com.epam.esm.dto;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public CertificateDTO toCertificateDto(GiftCertificate certificate) {
        String name = certificate.getName();
        String description = certificate.getDescription();
        int price = certificate.getPrice();
        int duration = certificate.getDuration();
        LocalDate createDate = certificate.getCreateDate().toLocalDate();
        List<String> tags = certificate
                .getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return new CertificateDTO(name, description, price, duration, createDate, tags);
    }

    public TagDTO toTagDto(Tag tag) {
        String name = tag.getName();

        return new TagDTO(name);
    }
}
