package com.epam.esm.service.implementation;

import com.epam.esm.dao.abstraction.TagDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.exeption_handling.exeptions.EmptyRequestBodyException;
import com.epam.esm.exeption_handling.exeptions.NoSuchDataException;
import com.epam.esm.service.abstraction.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
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
        return tagDao.insert(tag);
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
