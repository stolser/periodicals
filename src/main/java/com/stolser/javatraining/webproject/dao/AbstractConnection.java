package com.stolser.javatraining.webproject.dao;

/**
 * Represents an abstract connection.
 */
public interface AbstractConnection extends AutoCloseable {
    /**
     * Defines begin of transaction
     */
    void beginTransaction();

    /**
     * Saves transaction.
     */
    void commitTransaction();

    /**
     * rolls back transaction
     */
    void rollbackTransaction();

    /**
     * Closes connection.
     */
    @Override
    void close();

}
