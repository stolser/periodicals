package com.stolser.javatraining.webproject;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.database.ConnectionPool;
import com.stolser.javatraining.webproject.model.database.SqlConnectionPool;
import com.stolser.javatraining.webproject.model.service.factory.ServiceFactory;
import com.stolser.javatraining.webproject.model.service.factory.ServiceProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppRunner {
    private static ServiceFactory serviceFactory;
    private static ConnectionPool connectionPool;

    public static void main(String[] args) throws IOException {
        readDbConfigAndCreateServiceFactory();
//        createSchemaAndInsertData();

    }


    private static void readDbConfigAndCreateServiceFactory() throws IOException {
        String filename = ServiceProvider.DB_CONFIG_FILENAME;
        InputStream input = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(input);

        String url = properties.getProperty("database.url");
        String dbName = properties.getProperty("database.dbName");
        String userName = properties.getProperty("database.userName");
        String userPassword = properties.getProperty("database.userPassword");
        int maxConnNumber = Integer.valueOf(properties.getProperty("database.maxConnNumber"));

        connectionPool = SqlConnectionPool.getBuilder(url, dbName)
                .setUserName(userName)
                .setPassword(userPassword)
                .setMaxConnections(maxConnNumber)
                .build();

        DaoFactory daoFactory = DaoFactory.getMysqlDaoFactory();

        serviceFactory = ServiceProvider.newServiceFactory(daoFactory, connectionPool);
    }

    private static void createSchemaAndInsertData() {
        serviceFactory.getDbSetupService().createSchema();
    }
}
