package com.epam.esm.dao;

import java.util.List;

/**
 * Common interface for all dao.
 *
 * @param <T>  represents type of specific object
 * @param <K> represents type of identifier
 */
public interface GenericDao<T, K> {

    /**
     * Retrieves all object data from database.
     *
     * @return List of objects which represent one row in database.
     */
    List<T> findAll();

    /**
     * Insert object to a database.
     *
     * @param obj object to insert
     * @return inserted object
     */
    K insert(T obj);

    /**
     * Delete certain object identified by id from database.
     *
     * @param id identifier of the object.
     */
    K delete(K id);

}

