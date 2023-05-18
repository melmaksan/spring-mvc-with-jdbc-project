package com.epam.esm.certificate_service.service.impl;

import com.epam.esm.certificate_service.dao.TagDao;
import com.epam.esm.certificate_service.entities.Tag;
import com.epam.esm.certificate_service.service.TagService;
import com.epam.esm.certificate_service.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.certificate_service.exeption_handling.exeptions.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TagServiceImpl implements TagService {

    private static final String CODE = "02";

    @Autowired
    private TagDao tagDao;

    @Override
    public Tag getTagById(int id) {
        try {
            return tagDao.findById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("There is no tag with id '" + id + "' in DB", CODE);
        }
    }

    @Override
    public Tag getTagByName(String name) {
        try {
            return tagDao.findByName(name);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("There is no tag with name '" + name + "' in DB", CODE);
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Transactional
    @Override
    public int addTag(Tag tag) {
        if (tag.getName() == null) {
            throw new EmptyRequestBodyException("Field name is required, please try again!", CODE);
        }
        int id = tagDao.insert(tag);
        System.out.println("tag id = " + id);
        return id;
    }

    @Transactional
    @Override
    public int deleteTag(int id) {
        try {
            Tag tag = tagDao.findById(id);
            return tagDao.delete(tag.getId());
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("Can't delete tag with id '" + id +
                    "' because it doesn't exist in DB", CODE);
        }
    }

}
