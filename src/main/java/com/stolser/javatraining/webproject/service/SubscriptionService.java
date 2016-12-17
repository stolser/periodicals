package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAllByUserId(long id);
}
