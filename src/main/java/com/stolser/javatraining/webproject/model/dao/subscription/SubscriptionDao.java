package com.stolser.javatraining.webproject.model.dao.subscription;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription> {
    Subscription findOneByUserIdAndPeriodicalId(long userId, long periodicalId);

    /**
     * @return all subscriptions (active and expired) of the specified user
     */
    List<Subscription> findAllByUser(User user);
    List<Subscription> findAllByPeriodicalIdAndStatus(long periodicalId,
                                                      Subscription.Status status);
}
