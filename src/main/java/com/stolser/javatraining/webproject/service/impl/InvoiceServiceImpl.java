package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.invoice.InvoiceDao;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.storage.ConnectionPool;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.service.InvoiceService;

import java.sql.Connection;
import java.sql.SQLException;
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
        try (Connection conn = connectionPool.getConnection()) {

            return factory.getInvoiceDao(conn).findOneById(invoiceId);

        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Invoice> findAllByUserId(long userId) {
        try (Connection conn = connectionPool.getConnection()) {

            return factory.getInvoiceDao(conn).findAllByUserId(userId);

        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Invoice> findAllByPeriodicalId(long periodicalId) {
        try (Connection conn = connectionPool.getConnection()) {

            return factory.getInvoiceDao(conn).findAllByPeriodicalId(periodicalId);

        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public boolean createNew(Invoice invoice) {
        try (Connection conn = connectionPool.getConnection()) {

            factory.getInvoiceDao(conn).createNew(invoice);

            return true;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public boolean payInvoice(Invoice invoiceToPay) {
        Connection conn = connectionPool.getConnection();

        try {
            invoiceToPay.setStatus(Invoice.Status.PAID);
            invoiceToPay.setPaymentDate(Instant.now());

            User userFromDb = factory.getUserDao(conn).findOneById(invoiceToPay.getUser().getId());
            Periodical periodical = invoiceToPay.getPeriodical();

            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);
            Subscription existingSubscription = subscriptionDao
                    .findOneByUserIdAndPeriodicalId(userFromDb.getId(), periodical.getId());

            int subscriptionPeriod = invoiceToPay.getSubscriptionPeriod();

            InvoiceDao invoiceDao = factory.getInvoiceDao(conn);

            conn.setAutoCommit(false);
            invoiceDao.update(invoiceToPay);

            if (userDoesNotHaveSubscriptionOnThisPeriodical(existingSubscription)) {
                createAndPersistNewSubscription(userFromDb, periodical, subscriptionPeriod, subscriptionDao);

            } else {
                updateExistingSubscription(existingSubscription, subscriptionPeriod, subscriptionDao);
            }

            conn.commit();
            conn.setAutoCommit(true);

            return true;

        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (SQLException e1) {
                throw new StorageException(e);
            }

            throw new StorageException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException e) {
                    throw new StorageException(e);
                }
            }
        }
    }

    @Override
    public FinancialStatistics getFinStatistics(Instant since, Instant until) {
        try (Connection conn = connectionPool.getConnection()) {

            InvoiceDao dao = factory.getInvoiceDao(conn);
            long totalInvoiceSum = dao.getCreatedInvoiceSumByCreationDate(since, until);
            long paidInvoiceSum = dao.getPaidInvoiceSumByPaymentDate(since, until);

            return new FinancialStatistics(totalInvoiceSum, paidInvoiceSum);
        } catch (Exception e) {
            throw new StorageException(e);
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
        Subscription newSubscription = new Subscription();
        newSubscription.setUser(userFromDb);
        newSubscription.setPeriodical(periodical);
        newSubscription.setDeliveryAddress(userFromDb.getAddress());
        newSubscription.setEndDate(getEndDate(Instant.now(), subscriptionPeriod));
        newSubscription.setStatus(Subscription.Status.ACTIVE);

        subscriptionDao.createNew(newSubscription);
    }

    private boolean userDoesNotHaveSubscriptionOnThisPeriodical(Subscription existingSubscription) {
        return existingSubscription == null;
    }

    private Instant getEndDate(Instant startInstant, int subscriptionPeriod) {
        LocalDateTime startDate = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());

        return startDate.plusMonths(subscriptionPeriod).toInstant(ZoneOffset.UTC);
    }


}
