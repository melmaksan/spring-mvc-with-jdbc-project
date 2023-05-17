package com.epam.esm.dao;

import com.epam.esm.entities.Tag;

public interface TagDao extends GenericDao<Tag, Integer> {

    Tag findById(int id);

    Tag findByName(String name);

}
