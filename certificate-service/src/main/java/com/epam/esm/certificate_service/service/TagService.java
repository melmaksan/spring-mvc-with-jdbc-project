package com.epam.esm.certificate_service.service;

import com.epam.esm.certificate_service.entities.Tag;

import java.util.List;

public interface TagService {

    Tag getTagById(int id);

    Tag getTagByName(String name);

    List<Tag> getAllTags();

    int addTag(Tag tag);

    int deleteTag(int id);

}
