package com.stolser.javatraining.webproject.connection.pool;

import com.stolser.javatraining.webproject.dao.AbstractConnection;

/**
 * Represents an abstraction for reusable connection pool.
 */
public interface ConnectionPool {
    AbstractConnection getConnection();
}
