package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.InvoiceDao;
import com.stolser.javatraining.webproject.dao.SubscriptionDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.InvoiceService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private InvoiceServiceImpl() {
    }

    private static class InstanceHolder {
        private static final InvoiceServiceImpl INSTANCE = new InvoiceServiceImpl();
    }

    /**
     * @return a singleton object of this type
     */
    public static InvoiceServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public Invoice findOneById(long invoiceId) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getInvoiceDao(conn).findOneById(invoiceId);
        }
    }

    @Override
    public List<Invoice> findAllByUserId(long userId) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getInvoiceDao(conn).findAllByUserId(userId);
        }
    }

    @Override
    public List<Invoice> findAllByPeriodicalId(long periodicalId) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getInvoiceDao(conn).findAllByPeriodicalId(periodicalId);
        }
    }

    @Override
    public boolean createNew(Invoice invoice) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            factory.getInvoiceDao(conn).createNew(invoice);
            return true;
        }
    }

    @Override
    public boolean payInvoice(Invoice invoiceToPay) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);
            invoiceToPay.setStatus(Invoice.Status.PAID);
            invoiceToPay.setPaymentDate(Instant.now());

            User userFromDb = factory.getUserDao(conn).findOneById(invoiceToPay.getUser().getId());
            Periodical periodical = invoiceToPay.getPeriodical();

            Subscription existingSubscription = subscriptionDao
                    .findOneByUserIdAndPeriodicalId(userFromDb.getId(), periodical.getId());

            conn.beginTransaction();
            factory.getInvoiceDao(conn).update(invoiceToPay);

            int subscriptionPeriod = invoiceToPay.getSubscriptionPeriod();
            if (existingSubscription == null) {
                createAndPersistNewSubscription(userFromDb, periodical, subscriptionPeriod, subscriptionDao);

            } else {
                updateExistingSubscription(existingSubscription, subscriptionPeriod, subscriptionDao);
            }

            conn.commitTransaction();
            return true;
        }
    }

    @Override
    public FinancialStatistics getFinStatistics(Instant since, Instant until) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            InvoiceDao dao = factory.getInvoiceDao(conn);
            long totalInvoiceSum = dao.getCreatedInvoiceSumByCreationDate(since, until);
            long paidInvoiceSum = dao.getPaidInvoiceSumByPaymentDate(since, until);

            return new FinancialStatistics(totalInvoiceSum, paidInvoiceSum);
        }
    }

    private void updateExistingSubscription(Subscription existingSubscription,
                                            int subscriptionPeriod, SubscriptionDao subscriptionDao) {
        Instant newEndDate;

        if (Subscription.Status.INACTIVE.equals(existingSubscription.getStatus())) {
            newEndDate = getEndDate(Instant.now(), subscriptionPeriod);
        } else {
            newEndDate = getEndDate(existingSubscription.getEndDate(), subscriptionPeriod);
        }

        existingSubscription.setEndDate(newEndDate);
        existingSubscription.setStatus(Subscription.Status.ACTIVE);

        subscriptionDao.update(existingSubscription);
    }

    private void createAndPersistNewSubscription(User userFromDb, Periodical periodical,
                                                 int subscriptionPeriod, SubscriptionDao subscriptionDao) {
        Subscription.Builder builder = new Subscription.Builder();

        builder.setUser(userFromDb)
                .setPeriodical(periodical)
                .setDeliveryAddress(userFromDb.getAddress())
                .setEndDate(getEndDate(Instant.now(), subscriptionPeriod))
                .setStatus(Subscription.Status.ACTIVE);

        subscriptionDao.createNew(builder.build());
    }

    private Instant getEndDate(Instant startInstant, int subscriptionPeriod) {
        LocalDateTime startDate = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());

        return startDate.plusMonths(subscriptionPeriod).toInstant(ZoneOffset.UTC);
    }
}
