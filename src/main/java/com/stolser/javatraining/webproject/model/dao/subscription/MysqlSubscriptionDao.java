package com.stolser.javatraining.webproject.model.dao.subscription;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlSubscriptionDao implements SubscriptionDao {
    private Connection conn;

    public MysqlSubscriptionDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Subscription> findSubscriptionsByUser(User user) {
        String sqlStatement = "SELECT * FROM users " +
                "JOIN subscriptions ON (users.id = subscriptions.user_id) " +
                "JOIN periodicals ON (subscriptions.periodical_id = periodicals.id) " +
                "WHERE users.id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, user.getId());

            ResultSet rs = st.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();
            while (rs.next()) {
                Periodical periodical = new Periodical();
                periodical.setId(rs.getLong("periodicals.id"));
                periodical.setName(rs.getString("periodicals.name"));
                periodical.setCategory(rs.getString("periodicals.category"));
                periodical.setPublisher(rs.getString("periodicals.publisher"));
                periodical.setDescription(rs.getString("periodicals.description"));
                periodical.setOneMonthCost(rs.getDouble("periodicals.one_month_cost"));
                periodical.setStatus(Periodical.Status.valueOf(
                        rs.getString("periodicals.status").toUpperCase()));

                Subscription subscription = new Subscription();
                subscription.setId(rs.getLong("subscriptions.id"));
                subscription.setUser(user);
                subscription.setPeriodical(periodical);
                subscription.setDeliveryAddress(rs.getString("subscriptions.delivery_address"));
                subscription.setEndDate(rs.getTimestamp("subscriptions.end_date").toInstant());
                subscription.setStatus(Subscription.Status.valueOf(rs.getString("subscriptions.status")));

                subscriptions.add(subscription);
            }

            return subscriptions;

        } catch (SQLException e) {
            String message = String.format("Exception during retrieving subscriptions for a user: %s. " +
                    "Original: %s. ", user, e.getMessage());

            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public Subscription findOneById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Subscription> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createNew(Subscription subscription) {
        String sqlStatement = "INSERT INTO subscriptions " +
                "(user_id, periodical_id, delivery_address, end_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            setSubscriptionForInsertUpdateStatement(st, subscription);

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format("Exception during creating a subscription %s. " +
                    "Original: %s. ", subscription, e.getMessage());

            throw new CustomSqlException(message, e);
        }
    }

    private void setSubscriptionForInsertUpdateStatement(PreparedStatement st, Subscription subscription)
            throws SQLException {
        st.setLong(1, subscription.getUser().getId());
        st.setLong(2, subscription.getPeriodical().getId());
        st.setString(3, subscription.getDeliveryAddress());
        st.setTimestamp(4, new Timestamp(subscription.getEndDate().toEpochMilli()));
        st.setString(5, subscription.getStatus().name().toLowerCase());
    }

    @Override
    public void update(Subscription subscription) {
        String sqlStatement = "UPDATE subscriptions " +
                "SET user_id=?, periodical_id=?, delivery_address=?, end_date=?, status=? " +
                "WHERE id=?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            setSubscriptionForInsertUpdateStatement(st, subscription);
            st.setLong(6, subscription.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format("Exception during updating %s . " +
                    "Original: %s. ", subscription, e.getMessage());

            throw new CustomSqlException(message, e);
        }
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
