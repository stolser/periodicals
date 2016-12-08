package com.stolser.javatraining.webproject.model.service.invoice;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.invoice.InvoiceDao;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final DaoFactory factory;

    public InvoiceServiceImpl() {
        factory = DaoFactory.getMysqlDaoFactory();
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
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            return factory.getInvoiceDao(conn).findOneById(invoiceId);

        } catch (Exception e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public List<Invoice> findAllByUserId(long userId) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("InvoiceServiceImpl: connection has been got. Retrieving all invoices for " +
                    "user with id = " + userId);

            return factory.getInvoiceDao(conn).findAllByUserId(userId);

        } catch (Exception e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public boolean createNew(Invoice invoice) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("InvoiceServiceImpl: connection has been got. Creating a new invoice.");

            factory.getInvoiceDao(conn).createNew(invoice);

            return true;
        } catch (Exception e) {
            throw new CustomSqlException(e);
        }
    }

    /**
     * Update the status of this invoice to 'PAID' and update an existing subscription
     * (or create a new one) inside one transaction.
     * @param invoiceId invoice id to be paid
     * @return true if after committing this invoice in the db has status 'PAID' and
     * false otherwise.
     */
    @Override
    public boolean payInvoice(long invoiceId) {
        Connection conn = ConnectionPoolProvider.getPool().getConnection();

        LOGGER.error("InvoiceServiceImpl: connection has been got. Paying an invoice with " +
                "id = {}", invoiceId);

        try {
            InvoiceDao invoiceDao = factory.getInvoiceDao(conn);
            Invoice invoice = invoiceDao.findOneById(invoiceId);
            if ((invoice == null) || !invoice.getStatus().equals(Invoice.Status.NEW)) {
                return false;
//                throw new NoSuchElementException("There is no invoice in the db with id = " + invoiceId);
            }

            invoice.setStatus(Invoice.Status.PAID);
            invoice.setPaymentDate(Instant.now());

            User userFromDb = factory.getUserDao(conn).findOneById(invoice.getUser().getId());
            Periodical periodical = invoice.getPeriodical();

            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);
            Subscription existingSubscription = subscriptionDao
                    .findOneByUserIdAndPeriodicalId(userFromDb.getId(), periodical.getId());

            int subscriptionPeriod = invoice.getSubscriptionPeriod();

            conn.setAutoCommit(false);
            invoiceDao.update(invoice);

            if (existingSubscription == null) {
                Subscription newSubscription = new Subscription();
                newSubscription.setUser(userFromDb);
                newSubscription.setPeriodical(periodical);
                newSubscription.setDeliveryAddress(userFromDb.getAddress());
                newSubscription.setEndDate(getEndDate(Instant.now(), subscriptionPeriod));
                newSubscription.setStatus(Subscription.Status.ACTIVE);

                subscriptionDao.createNew(newSubscription);

            } else {
                Instant newEndDate;

                if (existingSubscription.getStatus().equals(Subscription.Status.INACTIVE)) {
                    newEndDate = getEndDate(Instant.now(), subscriptionPeriod);
                } else {
                    newEndDate = getEndDate(existingSubscription.getEndDate(), subscriptionPeriod);
                }

                existingSubscription.setEndDate(newEndDate);
                existingSubscription.setStatus(Subscription.Status.ACTIVE);

                subscriptionDao.update(existingSubscription);
            }

            conn.commit();
            conn.setAutoCommit(true);

            if (Invoice.Status.PAID.equals(invoiceDao.findOneById(invoiceId).getStatus())) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (SQLException e1) {
                throw new CustomSqlException(e);
            }

            throw new CustomSqlException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();

                } catch (SQLException e) {
                    throw new CustomSqlException(e);
                }
            }
        }
    }

    private Instant getEndDate(Instant startInstant, int subscriptionPeriod) {
        LocalDateTime startDate = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());


        return startDate.plusMonths(subscriptionPeriod).toInstant(ZoneOffset.UTC);
    }


}
