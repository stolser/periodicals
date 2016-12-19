package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceImplTest {
    private DaoFactory factory = mock(DaoFactory.class);
    @Mock
    private UserDao userDao;
    @Mock
    private SubscriptionDao subscriptionDao;
    private User user = new User();
    private Invoice invoice = new Invoice();
    private Subscription subscription = new Subscription();

    @Before
    public void setUp() throws Exception {

        user.setId(2);

        invoice.setId(10);
        invoice.setUser(user);

        subscription.setId(77);


        when(factory.getUserDao(any())).thenReturn(userDao);
        when(factory.getSubscriptionDao(any())).thenReturn(subscriptionDao);

        when(userDao.findOneById(any())).thenReturn(user);
        when(subscriptionDao.findOneByUserIdAndPeriodicalId(2, 10));
    }

    @Test
    public void payInvoice() throws Exception {

    }
}