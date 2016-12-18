package com.stolser.javatraining.webproject.model.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Reads database configuration data and creates a connection pool.
 */
public class ConnectionPoolProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolProvider.class);
    private static final String EXCEPTION_DURING_OPENING_DB_CONFIG_FILE =
            "Exception during opening the db-config file with path = {}.";
    private static final String EXCEPTION_DURING_LOADING_DB_CONFIG_PROPERTIES =
            "Exception during loading db-config properties from the file " +
            "(path = {})";
    private static final ConnectionPool INSTANCE;

    static {
        InputStream input;
        Properties properties = new Properties();
        try {
            input = ConnectionPoolProvider.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILENAME);
            properties.load(input);

        } catch (FileNotFoundException e) {
            LOGGER.error(EXCEPTION_DURING_OPENING_DB_CONFIG_FILE,
                    DB_CONFIG_FILENAME);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error(EXCEPTION_DURING_LOADING_DB_CONFIG_PROPERTIES, DB_CONFIG_FILENAME);
            throw new RuntimeException();
        }

        String url = properties.getProperty(DB_CONFIG_PARAM_URL);
        String dbName = properties.getProperty(DB_CONFIG_PARAM_DB_NAME);
        String userName = properties.getProperty(DB_CONFIG_PARAM_USER_NAME);
        String userPassword = properties.getProperty(DB_CONFIG_PARAM_USER_PASSWORD);
        int maxConnNumber = Integer.valueOf(properties.getProperty(DB_CONFIG_PARAM_MAX_CONN_NUMBER));

        INSTANCE = SqlConnectionPool.getBuilder(url, dbName)
                .setUserName(userName)
                .setPassword(userPassword)
                .setMaxConnections(maxConnNumber)
                .build();
    }

    public static ConnectionPool getPool() {
        return INSTANCE;
    }
}
