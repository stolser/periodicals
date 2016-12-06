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
    private Connection conn;

    public MysqlInvoiceDao(Connection conn) {
        this.conn = conn;
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
                invoice.setCreationDate(Instant.ofEpochMilli(rs.getDate("invoices.creation_date").getTime()));
                invoice.setPaymentDate(Instant.ofEpochMilli(rs.getDate("invoices.payment_date").getTime()));
                invoice.setStatus(Invoice.Status.valueOf(rs.getString("invoices.status").toUpperCase()));

                invoices.add(invoice);

            }

            return invoices;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }

    }

    @Override
    public void createNew(Invoice invoice) {
        String sqlStatement = "INSERT INTO invoices " +
                "(user_id, periodical_id, period, total_sum, creation_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            st.setLong(1, invoice.getUser().getId());
            st.setLong(2, invoice.getPeriodical().getId());
            st.setInt(3, invoice.getSubscriptionPeriod());
            st.setDouble(4, invoice.getTotalSum());
            st.setTimestamp(5, new Timestamp(invoice.getCreationDate().toEpochMilli()));
            st.setString(6, invoice.getStatus().toString().toLowerCase());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public Invoice findOneById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Invoice> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Invoice entity) {
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
