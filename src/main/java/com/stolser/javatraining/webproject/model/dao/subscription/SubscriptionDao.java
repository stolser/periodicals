package com.stolser.javatraining.webproject.model.dao.subscription;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription> {
    Subscription findOneByUserIdAndPeriodicalId(long userId, long periodicalId);
    List<Subscription> findAllByUser(User user);
}
