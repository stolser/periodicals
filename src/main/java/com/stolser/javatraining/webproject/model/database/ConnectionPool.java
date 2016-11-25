package com.stolser.javatraining.webproject.model.database;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    String getDriverClassName();
    String getUrl();
}
