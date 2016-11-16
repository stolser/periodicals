package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    /**
     * Returns whether a user with the given {@code id} exists.
     * @param id must not be null
     * @return {@code true} if an user with the given id exists, {@code false} otherwise
     * @throws IllegalArgumentException in case the given user id is null
     */
    boolean exists(long id) throws SQLException;

    /**
     * Retrieves a user by its id.
     * @param id must not be null
     * @return the user with the given id or {@code null} if none found
     */
    User findOne(long id) throws SQLException;

    /**
     * Returns all users from the db.
     * @return all users
     */
    List<User> findAll() throws SQLException;

    /**
     * Saves a given user. Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param user an object to be persisted
     * @return a persisted {@link User} object
     * @throws IllegalArgumentException in case the given user is null
     */
    User save(User user) throws SQLException;

    /**
     * Deletes the user with the given id.
     * @param id must not be null
     * @throws IllegalArgumentException in case the given id is null
     */
    void delete(long id) throws SQLException;

    /**
     * Delete all users from the db.
     */
    void deleteAll() throws SQLException;
}
