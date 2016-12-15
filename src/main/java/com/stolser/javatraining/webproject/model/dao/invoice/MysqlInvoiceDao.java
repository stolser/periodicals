package com.stolser.javatraining.webproject.model.dao.invoice;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class MysqlInvoiceDao implements InvoiceDao {
    private static final String EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_INVOICE_ID =
            "Exception during execution statement '%s' for invoiceId = %d.";
    private static final String EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_USER_ID =
            "Exception during execution statement '%s' for userId = %d.";
    private static final String EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_INVOICE =
            "Exception during execution statement '%s' for invoice = %s.";
    private Connection conn;

    public MysqlInvoiceDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Invoice findOneById(long invoiceId) {
        String sqlStatement = "SELECT * FROM invoices WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, invoiceId);

            ResultSet rs = st.executeQuery();

            return rs.next() ? getInvoiceFromRs(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_INVOICE_ID,
                    sqlStatement, invoiceId);
            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public List<Invoice> findAllByUserId(long userId) {
        String sqlStatement = "SELECT * FROM invoices " +
                "JOIN users ON (invoices.user_id = users.id) " +
                "WHERE users.id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, userId);

            ResultSet rs = st.executeQuery();

            List<Invoice> invoices = new ArrayList<>();

            while (rs.next()) {
                invoices.add(getInvoiceFromRs(rs));
            }

            return invoices;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_USER_ID,
                    sqlStatement, userId);
            throw new CustomSqlException(message, e);
        }

    }

    @Override
    public void createNew(Invoice invoice) {
        String sqlStatement = "INSERT INTO invoices " +
                "(user_id, periodical_id, period, total_sum, creation_date, payment_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            setCreateUpdateStatementFromInvoice(st, invoice);

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_INVOICE,
                    sqlStatement, invoice);
            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public void update(Invoice invoice) {
        String sqlStatement = "UPDATE invoices " +
                "SET user_id=?, periodical_id=?, period=?, total_sum=?, creation_date=?, " +
                "payment_date=?, status=? " +
                "WHERE id=?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            setCreateUpdateStatementFromInvoice(st, invoice);
            st.setLong(8, invoice.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT_FOR_INVOICE,
                    sqlStatement, invoice);
            throw new CustomSqlException(message, e);
        }
    }

    private Invoice getInvoiceFromRs(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getLong(DB_INVOICES_ID));

        User user = new User();
        user.setId(rs.getLong(DB_INVOICES_USER_ID));
        invoice.setUser(user);

        Periodical periodical = new Periodical();
        periodical.setId(rs.getLong(DB_INVOICES_PERIODICAL_ID));
        invoice.setPeriodical(periodical);

        invoice.setSubscriptionPeriod(rs.getInt(DB_INVOICES_PERIOD));
        invoice.setTotalSum(rs.getDouble(DB_INVOICES_TOTAL_SUM));
        invoice.setCreationDate(getCreationDateFromResults(rs));
        invoice.setPaymentDate(getPaymentDateFromResults(rs));
        invoice.setStatus(Invoice.Status.valueOf(rs.getString(DB_INVOICES_STATUS).toUpperCase()));

        return invoice;
    }

    private Instant getCreationDateFromResults(ResultSet rs) throws SQLException {
        return Instant.ofEpochMilli(rs.getTimestamp(DB_INVOICES_CREATION_DATE).getTime());
    }

    private Instant getPaymentDateFromResults(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(DB_INVOICES_PAYMENT_DATE);

        return (timestamp != null) ? Instant.ofEpochMilli(timestamp.getTime()) : null;
    }

    private void setCreateUpdateStatementFromInvoice(PreparedStatement st, Invoice invoice) throws SQLException {
        st.setLong(1, invoice.getUser().getId());
        st.setLong(2, invoice.getPeriodical().getId());
        st.setInt(3, invoice.getSubscriptionPeriod());
        st.setDouble(4, invoice.getTotalSum());
        st.setTimestamp(5, new Timestamp(invoice.getCreationDate().toEpochMilli()));
        st.setTimestamp(6, getPaymentDate(invoice));
        st.setString(7, invoice.getStatus().name().toLowerCase());
    }

    private Timestamp getPaymentDate(Invoice invoice) {
        Timestamp timestamp = null;
        Instant paymentDate = invoice.getPaymentDate();


        if (paymentDate != null) {
            timestamp = new Timestamp(paymentDate.toEpochMilli());
        }

        return timestamp;
    }

    @Override
    public List<Invoice> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll() {
        throw new UnsupportedOperationException();
    }

}
