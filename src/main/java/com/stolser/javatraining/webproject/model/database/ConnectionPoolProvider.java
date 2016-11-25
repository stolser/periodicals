package com.stolser.javatraining.webproject.model.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPoolProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolProvider.class);
    private static final String DB_CONFIG_FILENAME =
            "src\\main\\resources\\webProject\\config\\dbConfig.properties";
    private static final ConnectionPool INSTANCE;

    static {
        String filename = DB_CONFIG_FILENAME;
        InputStream input;
        Properties properties = new Properties();
        try {
            input = new FileInputStream(filename);
            properties.load(input);

        } catch (FileNotFoundException e) {
            LOGGER.debug("Exception during opening the db-config file with path = {}.",
                    DB_CONFIG_FILENAME);
            throw new RuntimeException();
        } catch (IOException e) {
            LOGGER.debug("Exception during loading db-config properties from the file " +
                    "(path = {})", DB_CONFIG_FILENAME);
            throw new RuntimeException();
        }

        String url = properties.getProperty("database.url");
        String dbName = properties.getProperty("database.dbName");
        String userName = properties.getProperty("database.userName");
        String userPassword = properties.getProperty("database.userPassword");
        int maxConnNumber = Integer.valueOf(properties.getProperty("database.maxConnNumber"));

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
