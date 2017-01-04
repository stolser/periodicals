package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.SubscriptionDao;
import com.stolser.javatraining.webproject.dao.UserDao;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.service.SubscriptionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriptionServiceImplTest {
    private static final int USER_ID = 1;
    @Mock
    private DaoFactory factory;
    @Mock
    private UserDao userDao;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection conn;
    @InjectMocks
    private SubscriptionService subscriptionService = SubscriptionServiceImpl.getInstance();
    private User user;
    private List<Subscription> userSubscriptions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        user = new User();
        userSubscriptions.add(new Subscription());

        when(connectionPool.getConnection()).thenReturn(conn);

        when(factory.getUserDao(conn)).thenReturn(userDao);
        when(factory.getSubscriptionDao(conn)).thenReturn(subscriptionDao);

        when(userDao.findOneById(USER_ID)).thenReturn(user);
        when(subscriptionDao.findAllByUser(user)).thenReturn(userSubscriptions);
    }

    @Test
    public void findAllByUserId_Should_ReturnAllSubscriptionOfTheUser() throws Exception {
        assertEquals(userSubscriptions, subscriptionService.findAllByUserId(USER_ID));

        verify(subscriptionDao).findAllByUser(user);
    }
}