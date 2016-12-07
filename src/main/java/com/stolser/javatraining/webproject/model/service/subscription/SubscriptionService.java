package com.stolser.javatraining.webproject.model.service.subscription;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {
    private final DaoFactory factory;
    public static final String NO_SUBSCRIPTION_WITH_ID_MESSAGE = "There is no subscription in the DB with id = %d";

    private SubscriptionService() {
        factory = DaoFactory.getMysqlDaoFactory();
    }

    private static class InstanceHolder {
        private static final SubscriptionService INSTANCE = new SubscriptionService();
    }

    /**
     * @return a singleton object of this type
     */
    public static SubscriptionService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public List<Subscription> findAllByUserId(long id) {
        List<Subscription> subscriptions;

        try(Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            UserDao userDao = factory.getUserDao(conn);
            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);

            User userInDb = userDao.findOneById(id);
            subscriptions = subscriptionDao.findAllByUser(userInDb);

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }

        return subscriptions;
    }
}
