package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.entity.user.Login;

import java.sql.SQLException;

public interface LoginDao {
    /**
     * Returns whether an login with the given {@code login} value exists.
     * @param login must not be null.
     * @return {@code true} if an login with the given id exists, {@code false} otherwise
     * @throws IllegalArgumentException in case the given login value is null
     */
    boolean exists(String login) throws SQLException;

    /**
     * Retrieves a login by its id.
     * @param id must not be null
     * @return the login with the given id or {@code null} if none found
     */
    Login findOne(long id) throws SQLException;

    /**
     * Saves a given login.
     * @param login an object to be persisted
     * @return a persisted {@link Login} object
     * @throws IllegalArgumentException in case the given login is null
     */
    void save(Login login) throws SQLException;

    /**
     * Deletes the login with the given id.
     * @param id must not be null
     * @throws IllegalArgumentException in case the given id is null
     */
    void delete(long id) throws SQLException;

}
