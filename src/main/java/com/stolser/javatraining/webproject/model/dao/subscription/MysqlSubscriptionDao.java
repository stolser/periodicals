package com.stolser.javatraining.webproject.model.dao.subscription;

import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class MysqlSubscriptionDao implements SubscriptionDao {
    private static final String EXCEPTION_MSG_FINDING_ALL_PERIODICALS_BY_USER_ID =
            "Exception during finding all periodicals for userId = %d, " +
            "periodicalId = %d";
    private static final String EXCEPTION_MSG_FINDING_ALL_BY_ID =
            "Exception during finding all periodicals for periodicalId = %d, " +
            "status = %s";
    private static final String EXCEPTION_MSG_RETRIEVING_SUBSCRIPTIONS_FOR_USER =
            "Exception during retrieving subscriptions for a user: %s.";
    private static final String EXCEPTION_MSG_CREATING_SUBSCRIPTION =
            "Exception during creating a subscription %s.";
    private static final String EXCEPTION_MSG_UPDATING = "Exception during updating %s.";
    private Connection conn;

    public MysqlSubscriptionDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Subscription findOneByUserIdAndPeriodicalId(long userId, long periodicalId) {
        String sqlStatement = "SELECT * FROM subscriptions " +
                "WHERE user_id = ? AND periodical_id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, userId);
            st.setLong(2, periodicalId);

            ResultSet rs = st.executeQuery();

            return rs.next() ? newSubscriptionFromRs(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_MSG_FINDING_ALL_PERIODICALS_BY_USER_ID,
                    userId, periodicalId);

            throw new StorageException(message, e);
        }
    }

    @Override
    public List<Subscription> findAllByPeriodicalIdAndStatus(long periodicalId,
                                                             Subscription.Status status) {
        String sqlStatement = "SELECT * FROM subscriptions " +
                "JOIN periodicals ON (subscriptions.periodical_id = periodicals.id) " +
                "WHERE periodicals.id = ? AND subscriptions.status = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, periodicalId);
            st.setString(2, status.name().toLowerCase());

            ResultSet rs = st.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();
            while (rs.next()) {
                subscriptions.add(newSubscriptionFromRs(rs));
            }

            return subscriptions;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_MSG_FINDING_ALL_BY_ID, periodicalId, status);

            throw new StorageException(message, e);
        }
    }

    private Subscription newSubscriptionFromRs(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getLong(DB_SUBSCRIPTIONS_ID));

        User user = new User();
        user.setId(rs.getLong(DB_SUBSCRIPTIONS_USER_ID));
        subscription.setUser(user);

        Periodical periodical = new Periodical();
        periodical.setId(rs.getLong(DB_SUBSCRIPTIONS_PERIODICAL_ID));
        subscription.setPeriodical(periodical);

        subscription.setDeliveryAddress(rs.getString(DB_SUBSCRIPTIONS_DELIVERY_ADDRESS));
        subscription.setEndDate(rs.getTimestamp(DB_SUBSCRIPTIONS_END_DATE).toInstant());
        subscription.setStatus(Subscription.Status.valueOf(
                rs.getString(DB_SUBSCRIPTIONS_STATUS).toUpperCase()));

        return subscription;
    }

    @Override
    public List<Subscription> findAllByUser(User user) {
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
                periodical.setId(rs.getLong(DB_PERIODICALS_ID));
                periodical.setName(rs.getString(DB_PERIODICALS_NAME));
                periodical.setCategory(PeriodicalCategory.valueOf(
                        rs.getString(DB_PERIODICALS_CATEGORY).toUpperCase()));
                periodical.setPublisher(rs.getString(DB_PERIODICALS_PUBLISHER));
                periodical.setDescription(rs.getString(DB_PERIODICALS_DESCRIPTION));
                periodical.setOneMonthCost(rs.getLong(DB_PERIODICALS_ONE_MONTH_COST));
                periodical.setStatus(Periodical.Status.valueOf(
                        rs.getString(DB_PERIODICALS_STATUS).toUpperCase()));

                Subscription subscription = new Subscription();
                subscription.setId(rs.getLong("subscriptions.id"));
                subscription.setUser(user);
                subscription.setPeriodical(periodical);
                subscription.setDeliveryAddress(rs.getString(DB_SUBSCRIPTIONS_DELIVERY_ADDRESS));
                subscription.setEndDate(rs.getTimestamp(DB_SUBSCRIPTIONS_END_DATE).toInstant());
                subscription.setStatus(Subscription.Status.valueOf(
                        rs.getString(DB_SUBSCRIPTIONS_STATUS).toUpperCase()));

                subscriptions.add(subscription);
            }

            return subscriptions;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_MSG_RETRIEVING_SUBSCRIPTIONS_FOR_USER,
                    user);

            throw new StorageException(message, e);
        }
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
            String message = String.format(EXCEPTION_MSG_CREATING_SUBSCRIPTION,
                    subscription);

            throw new StorageException(message, e);
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
    public int update(Subscription subscription) {
        String sqlStatement = "UPDATE subscriptions " +
                "SET user_id=?, periodical_id=?, delivery_address=?, end_date=?, status=? " +
                "WHERE id=?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            setSubscriptionForInsertUpdateStatement(st, subscription);
            st.setLong(6, subscription.getId());

            return st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_MSG_UPDATING, subscription);

            throw new StorageException(message, e);
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

}
