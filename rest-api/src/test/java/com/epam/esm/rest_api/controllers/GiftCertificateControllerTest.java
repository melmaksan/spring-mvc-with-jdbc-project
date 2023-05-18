package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.service.GiftCertificateService;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.epam.esm.certificate_service.entities.GiftCertificate;
import com.epam.esm.certificate_service.service.GiftCertificateToTagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GiftCertificateControllerTest {

    @Mock
    private GiftCertificateToTagService certificateToTagService;
    @Mock
    private TagService tagService;
    @Mock
    private GiftCertificateService certificateService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private MockMvc mockMvc;
    private GiftCertificate certificate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GiftCertificateController
                (certificateService, tagService,certificateToTagService, mapper)).build();


        certificate = new GiftCertificate();
        certificate.setId(20L);
        certificate.setName("certificate");
        certificate.setDescription("test");
        certificate.setPrice(100);
        certificate.setDuration(10);
        certificate.setCreateDate(LocalDateTime.of(2023, 5, 15, 12, 0));
        certificate.setLastUpdateDate(LocalDateTime.of(2023, 5, 15, 12, 0));
    }

    @Test
    void getCertificateById() throws Exception {
        when(certificateService.getGiftCertificateById(anyLong())).thenReturn(certificate);

        this.mockMvc.perform(get("/api/certificate/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }

    @Test
    void getGiftCertificatesByName() throws Exception {
        when(certificateService.getGiftCertificateByName(anyString())).thenReturn(certificate);

        this.mockMvc.perform(get("/api/certificates/findByName/{name}", "certificate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }

    @Test
    void getGiftCertificatesByPart() throws Exception {
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(certificate);

        when(certificateService.getGiftCertificatesByPart(anyString())).thenReturn(certificates);

        this.mockMvc.perform(get("/api/certificates/findByPart/{part}",
                certificate.getName().substring(0, 3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(certificates.size())))
                .andDo(print());
    }


    @Test
    void getAllCertificates() throws Exception {
        List<GiftCertificate> certificateList = getCertificateList();

        when(certificateService.getAllGiftCertificates()).thenReturn(certificateList);

        this.mockMvc.perform(get("/api/certificates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(certificateList.size())))
                .andDo(print());
    }

    private List<GiftCertificate> getCertificateList() {
        List<GiftCertificate> list = new ArrayList<>();
        GiftCertificate secondCertificate = new GiftCertificate();
        secondCertificate.setName("second");
        secondCertificate.setDescription("test");
        secondCertificate.setPrice(200);
        secondCertificate.setDuration(20);
        secondCertificate.setCreateDate(LocalDateTime.now());
        secondCertificate.setLastUpdateDate(LocalDateTime.now());

        list.add(certificate);
        list.add(secondCertificate);
        return list;
    }

    @Test
    void getCertificatesAscDate() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate)).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByDateAsc()).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/ascDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesDescDate() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate).reversed()).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByDateDesc()).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/descDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesAscName() throws Exception {
        List<GiftCertificate> list = getCertificateList()
                .stream().sorted(Comparator.comparing(GiftCertificate::getName)).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByNameAsc()).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/ascName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void getCertificatesDescName() throws Exception {
        List<GiftCertificate> list = getCertificateList().stream()
                .sorted(Comparator.comparing(GiftCertificate::getName).reversed()).collect(Collectors.toList());

        when(certificateService.sortGiftCertificatesByNameDesc()).thenReturn(list);

        this.mockMvc.perform(get("/api/certificates/descName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(list.size())))
                .andDo(print());
    }

    @Test
    void deleteCertificate() throws Exception {
        when(certificateService.deleteGiftCertificate(anyLong())).thenReturn(1L);

        this.mockMvc.perform(delete("/api/certificates/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Certificate with id = " + 1L + " was deleted"))
                .andDo(print());
    }

    @Test
    void addCertificate() throws Exception {
        when(certificateService.addGiftCertificate(certificate)).thenReturn(20L);
        when(certificateService.getGiftCertificateById(20L)).thenReturn(certificate);

        this.mockMvc.perform(post("/api/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(certificate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())))
                .andDo(print());
    }

    @Test
    void updateCertificate() throws Exception {
        when(certificateService.updateGiftCertificate(certificate)).thenReturn(20L);
        when(certificateService.getGiftCertificateById(20L)).thenReturn(certificate);

        this.mockMvc.perform(put("/api/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(certificate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(certificate.getName())));
    }
}