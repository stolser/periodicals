package com.stolser.javatraining.webproject.model.storage;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    String getDriverClassName();
    String getUrl();
}
