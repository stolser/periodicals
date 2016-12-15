package com.stolser.javatraining.webproject.model.dao.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlPeriodicalDao implements PeriodicalDao {
    private static final String INCORRECT_FIELD_NAME = "There is no case for such a fieldName." +
            "Fix it!";
    private static final String EXCEPTION_DURING_RETRIEVING_PERIODICAL =
            "Exception during retrieving a periodical with %s = %s. ";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM periodicals " +
            "WHERE id = ?";
    private static final String SELECT_ALL_BY_NAME = "SELECT * FROM periodicals " +
            "WHERE name = ?";
    private static final String EXCEPTION_DURING_RETRIEVING_ALL_PERIODICALS = "Exception during retrieving all periodicals.";
    private static final String RETRIEVING_ALL_BY_STATUS = "Exception during retrieving periodicals with" +
            "status '%s'.";
    private static final String EXCEPTION_DURING_INSERTING = "Exception during inserting %s into 'periodicals'.";
    private static final String EXCEPTION_DURING_UPDATING = "Exception during updating %s.";
    private static final String EXCEPTION_DURING_DELETING_DISCARDED_PERIODICALS = "Exception during deleting discarded periodicals.";
    private Connection conn;

    public MysqlPeriodicalDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Periodical findOneById(long id) {
        String sqlStatement = SELECT_ALL_BY_ID;

        return getPeriodicalFromDb(sqlStatement, id, ApplicationResources.DB_PERIODICALS_ID);
    }

    @Override
    public Periodical findOneByName(String name) {
        String sqlStatement = SELECT_ALL_BY_NAME;

        return getPeriodicalFromDb(sqlStatement, name, ApplicationResources.DB_PERIODICALS_NAME);
    }

    private Periodical getPeriodicalFromDb(String sqlStatement, Object fieldValue,
                                           String fieldName) {
        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            switch (fieldName) {
                case ApplicationResources.DB_PERIODICALS_ID:
                    st.setLong(1, (Long) fieldValue);
                    break;
                case ApplicationResources.DB_PERIODICALS_NAME:
                    st.setString(1, (String) fieldValue);
                    break;
                default:
                    throw new IllegalArgumentException(INCORRECT_FIELD_NAME);
            }

            ResultSet rs = st.executeQuery();

            return rs.next() ? getNextPeriodicalFromResults(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_RETRIEVING_PERIODICAL,
                    fieldName, fieldValue);
            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public List<Periodical> findAll() {
        String sqlStatement = "SELECT * FROM periodicals";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            ResultSet rs = st.executeQuery();

            List<Periodical> periodicals = new ArrayList<>();
            while (rs.next()) {
                Periodical periodical = getNextPeriodicalFromResults(rs);

                periodicals.add(periodical);
            }

            return periodicals;

        } catch (SQLException e) {
            String message = EXCEPTION_DURING_RETRIEVING_ALL_PERIODICALS;

            throw new CustomSqlException(message, e);
        }
    }

    private Periodical getNextPeriodicalFromResults(ResultSet rs) throws SQLException {
        Periodical periodical = new Periodical();

        periodical.setId(rs.getLong(ApplicationResources.DB_PERIODICALS_ID));
        periodical.setName(rs.getString(ApplicationResources.DB_PERIODICALS_NAME));
        periodical.setCategory(PeriodicalCategory.valueOf(
                rs.getString(ApplicationResources.DB_PERIODICALS_CATEGORY).toUpperCase()));
        periodical.setPublisher(rs.getString(ApplicationResources.DB_PERIODICALS_PUBLISHER));
        periodical.setDescription(rs.getString(ApplicationResources.DB_PERIODICALS_DESCRIPTION));
        periodical.setOneMonthCost(rs.getDouble(ApplicationResources.DB_PERIODICALS_ONE_MONTH_COST));
        periodical.setStatus(Periodical.Status.valueOf(
                rs.getString(ApplicationResources.DB_PERIODICALS_STATUS).toUpperCase()));

        return periodical;
    }

    @Override
    public List<Periodical> findAllByStatus(Periodical.Status status) {
        String sqlStatement = "SELECT * FROM periodicals " +
                "WHERE status = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, status.toString().toLowerCase());

            ResultSet rs = st.executeQuery();

            List<Periodical> periodicals = new ArrayList<>();
            while (rs.next()) {
                Periodical periodical = getNextPeriodicalFromResults(rs);

                periodicals.add(periodical);
            }

            return periodicals;

        } catch (SQLException e) {
            String message = String.format(RETRIEVING_ALL_BY_STATUS, status);

            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public void createNew(Periodical periodical) {
        String sqlStatement = "INSERT INTO periodicals " +
                "(name, category, publisher, description, one_month_cost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            setStatementFromPeriodical(st, periodical);

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_INSERTING,
                    periodical);

            throw new CustomSqlException(message, e);
        }
    }

    private void setStatementFromPeriodical(PreparedStatement st, Periodical periodical) throws SQLException {
        st.setString(1, periodical.getName());
        st.setString(2, periodical.getCategory().toString());
        st.setString(3, periodical.getPublisher());
        st.setString(4, periodical.getDescription());
        st.setDouble(5, periodical.getOneMonthCost());
        st.setString(6, periodical.getStatus().name().toLowerCase());
    }

    @Override
    public int update(Periodical periodical) {
        String sqlStatement = "UPDATE periodicals " +
                "SET name=?, category=?, publisher=?, description=?, one_month_cost=?, status=? " +
                "WHERE id=?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            setStatementFromPeriodical(st, periodical);
            st.setLong(7, periodical.getId());

            return st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_UPDATING, periodical);

            throw new CustomSqlException(message, e);
        }
    }

    /**
     * Updates a periodical and sets a new status 'discarded' only if there is no active subscriptions
     * of this periodical.
     * @return the number of affected rows: 0 - if the condition was not satisfied and updated
     * has not happened; 1 - if the status of this periodical has been changed to 'discarded'
     */
    @Override
    public int updateAndSetDiscarded(Periodical periodical) {
        String sqlStatement = "UPDATE periodicals AS p " +
                "SET name=?, category=?, publisher=?, description=?, one_month_cost=?, status=? " +
                "WHERE id=? AND 0 = (SELECT count(*) FROM subscriptions AS s " +
                "WHERE s.periodical_id = p.id AND s.status = ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            setStatementFromPeriodical(st, periodical);
            st.setLong(7, periodical.getId());
            st.setString(8, Subscription.Status.ACTIVE.name().toLowerCase());

//            try {
//                Thread.sleep(5_000);
                                    /*for testing racing conditions. If a subscriber creates
              a subscription during this time then this periodical cannot be discarded any more.*/
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            return st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_UPDATING, periodical);

            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public void deleteAllDiscarded() {
        String sqlStatement = "DELETE FROM periodicals " +
                "WHERE status = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString( 1, Periodical.Status.DISCARDED.name());

            st.executeUpdate();

        } catch (SQLException e) {
            String message = EXCEPTION_DURING_DELETING_DISCARDED_PERIODICALS;

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
