package com.stolser.javatraining.webproject.model.dao.subscription;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription> {
    List<Subscription> findSubscriptionsByUser(User user);
}
