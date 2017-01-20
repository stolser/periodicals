package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.service.SubscriptionService;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private SubscriptionServiceImpl() {
    }

    private static class InstanceHolder {
        private static final SubscriptionServiceImpl INSTANCE = new SubscriptionServiceImpl();
    }

    public static SubscriptionServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public List<Subscription> findAllByUserId(long id) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getSubscriptionDao(conn)
                    .findAllByUser(factory.getUserDao(conn).findOneById(id));
        }
    }
}
