package com.epam.esm.service;

import com.epam.esm.entities.Tag;

import java.util.List;

public interface TagService {

    Tag getTagById(int id);

    Tag getTagByName(String name);

    List<Tag> getAllTags();

    int addTag(Tag tag);

    int deleteTag(int id);

}
