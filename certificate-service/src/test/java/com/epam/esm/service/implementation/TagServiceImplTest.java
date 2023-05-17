package com.epam.esm.service.implementation;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    @InjectMocks
    private static TagService tagService;
    private static Tag tag;

    @BeforeAll
    static void setUp() {
        tagService = new TagServiceImpl();

        tag = new Tag();
        tag.setId(1);
        tag.setName("test");
    }

    @Test
    void getTagById() {
        when(tagDao.findById(tag.getId())).thenReturn(tag);

        Tag foundTag = tagService.getTagById(tag.getId());
        assertEquals(tag, foundTag);
    }

    @Test
    void getTagByName() {
        when(tagDao.findByName(tag.getName())).thenReturn(tag);

        Tag foundTag = tagService.getTagByName(tag.getName());
        assertEquals(tag, foundTag);
    }

    @Test
    void getAllTags() {
        List<Tag> tags = getTagList();

        when(tagDao.findAll()).thenReturn(tags);

        List<Tag> foundList = tagService.getAllTags();
        assertEquals(tags, foundList);
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
    void addTag() {
        when(tagDao.insert(tag)).thenReturn(1);

        int result = tagService.addTag(tag);
        assertEquals(1, result);
    }

    @Test
    void deleteTag() {
        when(tagDao.findById(tag.getId())).thenReturn(tag);
        when(tagDao.delete(tag.getId())).thenReturn(1);

        int del = tagService.deleteTag(tag.getId());
        assertEquals(1, del);
    }
}