package com.stolser.javatraining.webproject.model.service.dbsetup;

public interface DbSetupService {
    /**
     * Create an empty db for the project with all necessary tables.
     */
    void createSchema();
}
