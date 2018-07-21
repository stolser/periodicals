package com.stolser.javatraining.webproject.connection.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.util.Objects.isNull;

/**
 * Reads database configuration data and creates a connection pool.
 */
public class ConnectionPoolProvider {
    private static final String DB_CONFIG_FILENAME = "webproject/config/dbConfig.properties";
    private static final String TEST_DB_CONFIG_FILENAME = "webproject/config/testDbConfig.properties";
    private static final String DB_CONFIG_PARAM_URL = "database.url";
    private static final String DB_CONFIG_PARAM_DB_NAME = "database.dbName";
    private static final String DB_CONFIG_PARAM_USER_NAME = "database.userName";
    private static final String DB_CONFIG_PARAM_USER_PASSWORD = "database.userPassword";
    private static final String DB_CONFIG_PARAM_MAX_CONN_NUMBER = "database.maxConnNumber";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolProvider.class);
    private static final String EXCEPTION_DURING_OPENING_DB_CONFIG_FILE =
            "Exception during opening the db-config file with path = {}.";
    private static final String EXCEPTION_DURING_LOADING_DB_CONFIG_PROPERTIES =
            "Exception during loading db-config properties from the file "
                    + "(path = {})";
    private static final ConnectionPool INSTANCE;
    private static ConnectionPool TEST_INSTANCE;

    static {
        InputStream input;
        Properties properties = new Properties();
        try {
            input = ConnectionPoolProvider.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILENAME);
            properties.load(input);
            INSTANCE = createPoolFromProperties(properties);

        } catch (FileNotFoundException e) {
            LOGGER.error(EXCEPTION_DURING_OPENING_DB_CONFIG_FILE,
                    DB_CONFIG_FILENAME);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error(EXCEPTION_DURING_LOADING_DB_CONFIG_PROPERTIES, DB_CONFIG_FILENAME);
            throw new RuntimeException();
        }
    }

    private static ConnectionPool createPoolFromProperties(Properties properties) {
        String url = properties.getProperty(DB_CONFIG_PARAM_URL);
        String dbName = properties.getProperty(DB_CONFIG_PARAM_DB_NAME);
        String userName = properties.getProperty(DB_CONFIG_PARAM_USER_NAME);
        String userPassword = properties.getProperty(DB_CONFIG_PARAM_USER_PASSWORD);
        int maxConnNumber = Integer.parseInt(properties.getProperty(DB_CONFIG_PARAM_MAX_CONN_NUMBER));

        return SqlConnectionPool.getBuilder(url, dbName)
                .setUserName(userName)
                .setPassword(userPassword)
                .setMaxConnections(maxConnNumber)
                .build();
    }

    public static ConnectionPool getPool() {
        return INSTANCE;
    }

    /**
     * @return a connection pool for using in tests.
     */
    public static ConnectionPool getTestPool() {
        if (isNull(TEST_INSTANCE)) {
            try {
                InputStream input = ConnectionPoolProvider.class.getClassLoader()
                        .getResourceAsStream(TEST_DB_CONFIG_FILENAME);
                Properties testProperties = new Properties();
                testProperties.load(input);

                TEST_INSTANCE = createPoolFromProperties(testProperties);

            } catch (FileNotFoundException e) {
                LOGGER.error(EXCEPTION_DURING_OPENING_DB_CONFIG_FILE,
                        TEST_DB_CONFIG_FILENAME);
                throw new RuntimeException(e);
            } catch (IOException e) {
                LOGGER.error(EXCEPTION_DURING_LOADING_DB_CONFIG_PROPERTIES, TEST_DB_CONFIG_FILENAME);
                throw new RuntimeException(e);
            }
        }

        return TEST_INSTANCE;
    }
}
