package com.stolser.javatraining.webproject.model.dao;

import java.util.List;

public interface GenericDao<E> {
    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null
     * @return the entity with the given id or {@code null} if none found
     */
    E findOneById(long id);

    /**
     * Returns all entities from the db.
     *
     * @return all entities
     */
    List<E> findAll();

    /**
     * Creates a new entity taking values for the fields from the passed entity.
     * If a passed entity has the 'id' field it is ignored.
     *
     * @param entity an object to be persisted
     * @return a persisted entity
     * @throws IllegalArgumentException in case the given entity is null
     */
    void createNew(E entity);

    /**
     * Updates an entity in the db. If the passed entity has such an 'id' that there is no
     * entity in the db with it, the method throws a {@link java.util.NoSuchElementException}.
     *
     * @param entity an object to be updated
     * @return an updated entity
     */
    int update(E entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be null
     */
    boolean delete(long id);

    /**
     * Delete all entities from the db.
     */
    boolean deleteAll();
}
