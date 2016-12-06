package com.stolser.javatraining.webproject.model.service.invoice;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.sql.Connection;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
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
    public void createNew(Invoice invoice) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("InvoiceServiceImpl: connection has been got. Creating a new invoice.");

            factory.getInvoiceDao(conn).createNew(invoice);

        } catch (Exception e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public void makeInvoicePaid(Invoice invoice) {
        // a transaction;
    }

    @Override
    public List<Invoice> findAllByUserId(long id) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("InvoiceServiceImpl: connection has been got. Retrieving all invoices for " +
                    "user with id = " + id);

            return factory.getInvoiceDao(conn).findAllByUserId(id);

        } catch (Exception e) {
            throw new CustomSqlException(e);
        }
    }


}
