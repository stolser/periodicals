package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.storage.ConnectionPool;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.service.SubscriptionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private SubscriptionServiceImpl() {
    }

    private static class InstanceHolder {
        private static final SubscriptionServiceImpl INSTANCE = new SubscriptionServiceImpl();
    }

    /**
     * @return a singleton object of this type
     */
    public static SubscriptionServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public List<Subscription> findAllByUserId(long id) {
        List<Subscription> subscriptions;

        try(Connection conn = connectionPool.getConnection()) {
            UserDao userDao = factory.getUserDao(conn);
            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);

            User userInDb = userDao.findOneById(id);
            subscriptions = subscriptionDao.findAllByUser(userInDb);

        } catch (SQLException e) {
            throw new StorageException(e);
        }

        return subscriptions;
    }
}
