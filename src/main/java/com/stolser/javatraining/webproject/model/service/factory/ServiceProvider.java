package com.stolser.javatraining.webproject.model.service.factory;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.database.ConnectionPool;

public class ServiceProvider {
    public static final String DB_CONFIG_FILENAME =
            "src\\main\\resources\\webProject\\config\\dbConfig.properties";

    public static ServiceFactory newServiceFactory(DaoFactory daoFactory, ConnectionPool pool){
        ServiceFactoryImpl.setUp(daoFactory, pool);

        return ServiceFactoryImpl.getInstance();
    }
}
