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
import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InvoiceServiceImplTest {
    private static final long USER_ID = 2L;
    private static final long PERIODICAL_ID = 5L;
    private static final long INVOICE_ID = 10L;
    private static final long SUBSCRIPTION_ID = 77L;
    @Mock
    private DaoFactory factory;
    @Mock
    private UserDao userDao;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private InvoiceDao invoiceDao;
    @Mock
    private Connection conn;
    @Mock
    private User user;
    @Mock
    private ConnectionPool connectionPool;

    private Invoice invoice = new Invoice();
    private Subscription subscription = mock(Subscription.class);
    private Periodical periodical = new Periodical();

    @InjectMocks
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(user.getId()).thenReturn(USER_ID);
        when(user.getAddress()).thenReturn("Some address");

        periodical.setId(PERIODICAL_ID);

        invoice.setId(INVOICE_ID);
        invoice.setUser(user);
        invoice.setPeriodical(periodical);
        invoice.setSubscriptionPeriod(1);

        when(subscription.getId()).thenReturn(SUBSCRIPTION_ID);
        when(subscription.getStatus()).thenReturn(Subscription.Status.INACTIVE);

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

    @Test
    public void payInvoice_Should_CreateNewSubscriptionIfItDoesNotExist() {
        when(subscriptionDao.findOneByUserIdAndPeriodicalId(USER_ID, PERIODICAL_ID))
                .thenReturn(null);

        assertTrue(invoiceService.payInvoice(invoice));

        verify(user, times(2)).getId();
        verify(user, times(1)).getAddress();
        verify(subscriptionDao, times(1)).createNew(any());
    }

    @Test(expected = StorageException.class)
    public void payInvoice_Should_CallRollbackIfExceptionIsThrown() throws SQLException {
        when(subscriptionDao.update(any())).thenThrow(StorageException.class);

        invoiceService.payInvoice(invoice);

        verify(conn).rollback();
    }
}