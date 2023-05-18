package com.epam.esm.certificate_service.dao;

import com.epam.esm.certificate_service.entities.Tag;

public interface TagDao extends GenericDao<Tag, Integer> {

    Tag findById(int id);

    Tag findByName(String name);

}
