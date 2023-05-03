package com.epam.esm.dao.abstraction;

import com.epam.esm.entities.Tag;

public interface TagDao extends GenericDao<Tag, Integer> {

    Tag findById(int id);

    Tag findByName(String name);

}
