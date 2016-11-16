package com.stolser.javatraining.webproject.model.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class SqlConnectionPool implements ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlConnectionPool.class);
    private static final String USER_NAME_DEFAULT = "test";
    private static final String USER_PASSWORD_DEFAULT = "test";
    private static final String DRIVER_NAME_DEFAULT = "com.mysql.jdbc.Driver";
    private static final int MAX_TOTAL_CONNECTIONS = 10;
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
            url += "?useSSL=false";
        }

        return url;
    }

    public static Builder getBuilder(String url, String dbName) {
        return new Builder(url, dbName);
    }

    public Connection getConnection() throws SQLException {
        Connection newConn = dataSource.getConnection();
        LOGGER.debug("A connection is got from {}", this);

        return newConn;
    }

    @Override
    public String getDriverClassName() {
        return dataSource.getDriverClassName();
    }

    @Override
    public String getUrl() {
        return dataSource.getUrl();
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
            checkNotNull(url, "url should not be null.");
            checkNotNull(url, "dbName should not be null.");
            this.url = url;
            this.dbName = dbName;
            this.driverClassName = DRIVER_NAME_DEFAULT;
            this.userName = USER_NAME_DEFAULT;
            this.password = USER_PASSWORD_DEFAULT;
            this.maxConnections = MAX_TOTAL_CONNECTIONS;
        }

        public Builder setDriverClassName(String driverClassName) {
            checkNotNull(driverClassName, "driverClassName should not be null.");
            this.driverClassName = driverClassName;
            return this;
        }

        public Builder setUserName(String userName) {
            checkNotNull(userName, "userName should not be null.");
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            checkNotNull(password, "password should not be null.");
            this.password = password;
            return this;
        }

        public Builder setUseSSL(boolean useSSL) {
            this.useSSL = useSSL;
            return this;
        }

        public Builder setMaxConnections(int maxConnections) {
            checkArgument(maxConnections > 0, "maxConnections should be a positive number.");
            this.maxConnections = maxConnections;
            return this;
        }

        public ConnectionPool build() {
            return new SqlConnectionPool(this);
        }

    }
}
