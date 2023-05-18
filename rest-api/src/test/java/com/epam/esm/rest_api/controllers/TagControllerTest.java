package com.epam.esm.rest_api.controllers;

import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.rest_api.dto.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TagControllerTest {

    @Mock
    private TagService tagService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private MockMvc mockMvc;
    private Tag tag;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TagController(tagService, mapper)).build();

        tag = new Tag();
        tag.setName("test");
    }

    @Test
    void showTag() throws Exception {
        when(tagService.getTagById(anyInt())).thenReturn(tag);

        this.mockMvc.perform(get("/api/tag/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tag.getName())));
    }

    @Test
    void getGiftCertificatesByName() throws Exception {
        when(tagService.getTagByName(anyString())).thenReturn(tag);

        this.mockMvc.perform(get("/api/tag/findByName/{name}", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tag.getName())));
    }

    @Test
    void showTags() throws Exception {
        List<Tag> tags = getTagList();

        when(tagService.getAllTags()).thenReturn(tags);

        this.mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(tags.size())))
                .andDo(print());
    }

    private List<Tag> getTagList() {
        List<Tag> tags = new ArrayList<>();
        Tag secondTag = new Tag();
        secondTag.setId(2);
        secondTag.setName("second");

        tags.add(tag);
        tags.add(secondTag);
        return tags;
    }

    @Test
    void createTag() throws Exception {
        when(tagService.addTag(tag)).thenReturn(30);
        when(tagService.getTagById(30)).thenReturn(tag);

        System.out.println(objectMapper.writeValueAsString(tag));

        this.mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tag.getName())))
                .andDo(print());
    }

    @Test
    void deleteTag() throws Exception {
        when(tagService.deleteTag(anyInt())).thenReturn(1);

        this.mockMvc.perform(delete("/api/tags/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Tag with id = " + 1 + " was deleted"))
                .andDo(print());
    }
}