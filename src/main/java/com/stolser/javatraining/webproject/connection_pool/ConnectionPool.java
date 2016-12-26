package com.stolser.javatraining.webproject.connection_pool;

import java.sql.Connection;

/**
 * Represents an abstraction for reusable connection pool.
 */
public interface ConnectionPool {
    Connection getConnection();
}
