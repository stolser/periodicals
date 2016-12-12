package com.stolser.javatraining.webproject.model.dao.invoice;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MysqlInvoiceDao implements InvoiceDao {
    private static final String SELECT_FROM_INVOICES_WHERE_ID = "SELECT * FROM invoices WHERE id = ?";

    private Connection conn;

    public MysqlInvoiceDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Invoice findOneById(long invoiceId) {
        String sqlStatement = SELECT_FROM_INVOICES_WHERE_ID;

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, invoiceId);

            ResultSet rs = st.executeQuery();

            Invoice invoice = null;
            if (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getLong("id"));

                User user = new User();
                user.setId(rs.getLong("user_id"));
                invoice.setUser(user);

                Periodical periodical = new Periodical();
                periodical.setId(rs.getLong("periodical_id"));
                invoice.setPeriodical(periodical);

                invoice.setSubscriptionPeriod(rs.getInt("period"));
                invoice.setTotalSum(rs.getDouble("total_sum"));
                invoice.setCreationDate(getCreationDateFromResults(rs));
                invoice.setPaymentDate(getPaymentDateFromResults(rs));
                invoice.setStatus(Invoice.Status.valueOf(rs.getString("status").toUpperCase()));
            }

            return invoice;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
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

                Invoice invoice = new Invoice();
                invoice.setId(rs.getLong("id"));

                User user = new User();
                user.setId(rs.getLong("invoices.user_id"));
                invoice.setUser(user);

                Periodical periodical = new Periodical();
                periodical.setId(rs.getLong("invoices.periodical_id"));
                invoice.setPeriodical(periodical);

                invoice.setSubscriptionPeriod(rs.getInt("invoices.period"));
                invoice.setTotalSum(rs.getDouble("invoices.total_sum"));
                invoice.setCreationDate(getCreationDateFromResults(rs));
                invoice.setPaymentDate(getPaymentDateFromResults(rs));
                invoice.setStatus(Invoice.Status.valueOf(rs.getString("invoices.status").toUpperCase()));

                invoices.add(invoice);

            }

            return invoices;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }

    }

    private Instant getCreationDateFromResults(ResultSet rs) throws SQLException {
        return Instant.ofEpochMilli(rs.getTimestamp("invoices.creation_date").getTime());
    }

    private Instant getPaymentDateFromResults(ResultSet rs) throws SQLException {
        Instant paymentDate = null;
        Timestamp timestamp = rs.getTimestamp("invoices.payment_date");

        if (timestamp != null) {
            paymentDate = Instant.ofEpochMilli(timestamp.getTime());
        }

        return paymentDate;
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
            throw new CustomSqlException(e);
        }
    }

    private void setCreateUpdateStatementFromInvoice(PreparedStatement st, Invoice invoice) throws SQLException {
        st.setLong(1, invoice.getUser().getId());
        st.setLong(2, invoice.getPeriodical().getId());
        st.setInt(3, invoice.getSubscriptionPeriod());
        st.setDouble(4, invoice.getTotalSum());
        st.setTimestamp(5, new Timestamp(invoice.getCreationDate().toEpochMilli()));
        st.setTimestamp(6, getPaymentDate(invoice));
        st.setString(7, invoice.getStatus().toString().toLowerCase());
    }

    private Timestamp getPaymentDate(Invoice invoice) {
        Timestamp timestamp = null;
        Instant paymentDate = invoice.getPaymentDate();


        if (paymentDate != null) {
            timestamp = new Timestamp(paymentDate.toEpochMilli());
        }

        return timestamp;
    }

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
            throw new CustomSqlException(e);
        }
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
