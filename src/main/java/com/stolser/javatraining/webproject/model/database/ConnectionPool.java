package com.stolser.javatraining.webproject.model.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;
    String getDriverClassName();
    String getUrl();
}
