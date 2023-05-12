package com.epam.esm.controllers;

import com.epam.esm.dao.abstraction.TagDao;
import com.epam.esm.dto.Mapper;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.abstraction.TagService;
import com.epam.esm.service.implementation.TagServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

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

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration("classpath:testApplicationContext.xml")
@Transactional
class TagControllerTest {

    @Mock
    private TagDao tagDao;
    @InjectMocks
    private final TagService tagService = new TagServiceImpl();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;
    private MockMvc mockMvc;
    private Tag tag;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TagController(tagService, mapper)).build();

        tag = new Tag();
        tag.setName("test");
    }

    @Test
    void showTag() throws Exception {
        when(tagService.getTagById(anyInt())).thenReturn(tag);

        this.mockMvc.perform(get("/api/tag/{id}", 1L))
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
        when(tagDao.insert(tag)).thenReturn(30);
        when(tagService.addTag(tag)).thenReturn(30);
        when(tagService.getTagById(30)).thenReturn(tag);

        this.mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tag.getName())))
                .andDo(print());
    }

    @Test
    void deleteTag() throws Exception {
        when(tagDao.findById(anyInt())).thenReturn(tag);
        when(tagDao.delete(anyInt())).thenReturn(1);
        when(tagService.deleteTag(anyInt())).thenReturn(1);

        this.mockMvc.perform(delete("/api/tags/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Tag with id = " + 1 + " was deleted"))
                .andDo(print());
    }
}