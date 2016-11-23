package com.stolser.javatraining.webproject.model.service.factory;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.database.ConnectionPool;
import com.stolser.javatraining.webproject.model.service.dbsetup.DbSetupService;
import com.stolser.javatraining.webproject.model.service.dbsetup.DbSetupServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;

public class ServiceFactoryImpl implements ServiceFactory {
    private static DaoFactory staticDaoFactory;
    private static ConnectionPool staticPool;

    private DaoFactory daoFactory;
    private ConnectionPool pool;

    private ServiceFactoryImpl(DaoFactory daoFactory, ConnectionPool pool) {
        this.daoFactory = daoFactory;
        this.pool = pool;
    }

    @Override
    public DbSetupService getDbSetupService() {
        DbSetupServiceImpl.setUp(pool);

        return DbSetupServiceImpl.getInstance();
    }

    public static void setUp(DaoFactory loginDao, ConnectionPool pool) {
        checkNotNull(loginDao, "loginDao should not be null");
        checkNotNull(pool, "pool should not be null");

        staticDaoFactory = loginDao;
        staticPool = pool;
    }

    public static ServiceFactory getInstance() {
        if ((staticDaoFactory == null) || (staticPool == null)) {
            throw new IllegalStateException("You must call setUp() before using getInstance().");
        }

        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final ServiceFactory INSTANCE =
                new ServiceFactoryImpl(staticDaoFactory, staticPool);
    }
}
