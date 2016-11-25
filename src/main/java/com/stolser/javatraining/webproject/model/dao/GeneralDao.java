package com.stolser.javatraining.webproject.model.dao;

import java.util.List;

public interface GeneralDao<E> {
    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null
     * @return the entity with the given id or {@code null} if none found
     */
    E findOne(long id);

    /**
     * Returns all entities from the db.
     *
     * @return all entities
     */
    List<E> findAll();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @param entity an object to be persisted
     * @return a persisted entity
     * @throws IllegalArgumentException in case the given entity is null
     */
    E save(E entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be null
     */
    void delete(long id);

    /**
     * Delete all entities from the db.
     */
    void deleteAll();
}
