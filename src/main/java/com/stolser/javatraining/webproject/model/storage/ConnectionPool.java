package com.stolser.javatraining.webproject.model.storage;

import java.sql.Connection;

/**
 * Represents an abstraction for reusable connection storage.
 */
public interface ConnectionPool {
    Connection getConnection();
}
