package com.stolser.javatraining.webproject.connection.pool;

import com.stolser.javatraining.webproject.dao.exception.StorageException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

class SqlConnectionPool implements ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlConnectionPool.class);
    private static final String USER_NAME_DEFAULT = "test";
    private static final String USER_PASSWORD_DEFAULT = "test";
    private static final String DRIVER_NAME_DEFAULT = "com.mysql.jdbc.Driver";
    private static final int MAX_TOTAL_CONNECTIONS = 10;
    private static final String USE_SSL_FALSE = "?useSSL=false";
    private static final String CONNECTION_EXCEPTION_TEXT =
            "Exception during getting a connection from a dataSource.";
    private static final String URL_SHOULD_NOT_BE_NULL = "url should not be null.";
    private static final String DB_NAME_SHOULD_NOT_BE_NULL = "dbName should not be null.";
    private static final String DRIVER_CLASS_NAME_SHOULD_NOT_BE_NULL = "driverClassName should not be null.";
    private static final String USER_NAME_SHOULD_NOT_BE_NULL = "userName should not be null.";
    private static final String PASSWORD_SHOULD_NOT_BE_NULL = "password should not be null.";
    private static final String MAX_CONNECTIONS_SHOULD_BE_A_POSITIVE_NUMBER = "maxConnections should be a positive number.";
    private BasicDataSource dataSource;
    private String description;

    private SqlConnectionPool(Builder builder) {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(builder.driverClassName);
        dataSource.setUrl(generateUrl(builder));
        dataSource.setUsername(builder.userName);
        dataSource.setPassword(builder.password);
        dataSource.setMaxTotal(builder.maxConnections);
        description =  builder.url + builder.dbName;
    }

    private String generateUrl(Builder builder) {
        String url = builder.url + builder.dbName;
        if (! builder.useSSL) {
            url += USE_SSL_FALSE;
        }

        return url;
    }

    public static Builder getBuilder(String url, String dbName) {
        return new Builder(url, dbName);
    }

    public Connection getConnection() {
        Connection newConn;

        try {
            newConn = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(CONNECTION_EXCEPTION_TEXT, e);
            throw new StorageException(e);
        }

        return newConn;
    }

    @Override
    public String toString() {
        return description;
    }

    public static class Builder {
        private String driverClassName;
        private String url;
        private String dbName;
        private String userName;
        private String password;
        private int maxConnections;
        private boolean useSSL;

        private Builder(String url, String dbName) {
            checkNotNull(url, URL_SHOULD_NOT_BE_NULL);
            checkNotNull(url, DB_NAME_SHOULD_NOT_BE_NULL);
            this.url = url;
            this.dbName = dbName;
            this.driverClassName = DRIVER_NAME_DEFAULT;
            this.userName = USER_NAME_DEFAULT;
            this.password = USER_PASSWORD_DEFAULT;
            this.maxConnections = MAX_TOTAL_CONNECTIONS;
        }

        public Builder setDriverClassName(String driverClassName) {
            checkNotNull(driverClassName, DRIVER_CLASS_NAME_SHOULD_NOT_BE_NULL);
            this.driverClassName = driverClassName;
            return this;
        }

        public Builder setUserName(String userName) {
            checkNotNull(userName, USER_NAME_SHOULD_NOT_BE_NULL);
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            checkNotNull(password, PASSWORD_SHOULD_NOT_BE_NULL);
            this.password = password;
            return this;
        }

        public Builder setUseSSL(boolean useSSL) {
            this.useSSL = useSSL;
            return this;
        }

        public Builder setMaxConnections(int maxConnections) {
            checkArgument(maxConnections > 0, MAX_CONNECTIONS_SHOULD_BE_A_POSITIVE_NUMBER);
            this.maxConnections = maxConnections;
            return this;
        }

        public ConnectionPool build() {
            return new SqlConnectionPool(this);
        }
    }
}
