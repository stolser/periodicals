package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.invoice.InvoiceDao;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.storage.ConnectionPool;
import com.stolser.javatraining.webproject.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.time.Instant;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InvoiceServiceImplTest {
    private static final long USER_ID = 2L;
    private static final long PERIODICAL_ID = 5L;
    private static final long INVOICE_ID = 10L;
    private static final long SUBSCRIPTION_ID = 77L;
    private DaoFactory factory = mock(DaoFactory.class);
    @Mock
    private UserDao userDao;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private InvoiceDao invoiceDao;
    private User user = new User();
    private Invoice invoice = new Invoice();
    private Subscription subscription = mock(Subscription.class);
    private Periodical periodical = new Periodical();
    private ConnectionPool connectionPool = mock(ConnectionPool.class);
    @InjectMocks
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        user.setId(USER_ID);
        user.setAddress("Some address");

        periodical.setId(PERIODICAL_ID);

        invoice.setId(INVOICE_ID);
        invoice.setUser(user);
        invoice.setPeriodical(periodical);
        invoice.setSubscriptionPeriod(1);

        when(subscription.getId()).thenReturn(SUBSCRIPTION_ID);
        when(subscription.getStatus()).thenReturn(Subscription.Status.INACTIVE);

        conn = mock(Connection.class);
        when(connectionPool.getConnection()).thenReturn(conn);


        when(subscriptionDao.findOneByUserIdAndPeriodicalId(USER_ID, PERIODICAL_ID))
                .thenReturn(subscription);

        when(factory.getUserDao(conn)).thenReturn(userDao);
        when(factory.getSubscriptionDao(conn)).thenReturn(subscriptionDao);
        when(factory.getInvoiceDao(conn)).thenReturn(invoiceDao);

        when(userDao.findOneById(USER_ID)).thenReturn(user);

    }

    @Test
    public void payInvoice_Should_UpdateInvoiceAndSubscription() throws Exception {
        assertTrue(invoiceService.payInvoice(invoice));

        verify(conn, times(1)).setAutoCommit(false);
        verify(conn, times(1)).setAutoCommit(true);
        verify(conn, times(1)).commit();

        verify(invoiceDao, times(1)).update(invoice);

        verify(subscription, times(0)).getEndDate();
        verify(subscription, times(1)).setEndDate(any());
        verify(subscription, times(1)).setStatus(Subscription.Status.ACTIVE);

        verify(subscriptionDao, times(1)).update(subscription);

    }

    @Test
    public void payInvoice_Should_GetEndDateIfSubscriptionIsActive() throws Exception {
        when(subscription.getStatus()).thenReturn(Subscription.Status.ACTIVE);
        when(subscription.getEndDate()).thenReturn(Instant.now());

        assertTrue(invoiceService.payInvoice(invoice));

        verify(subscription, times(1)).getEndDate();

    }
}