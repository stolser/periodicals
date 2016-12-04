package com.stolser.javatraining.webproject.model.dao.periodical;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlPeriodicalDao implements PeriodicalDao {
    private Connection conn;

    public MysqlPeriodicalDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Periodical findOneById(long id) {
        String sqlStatement = "SELECT * FROM periodicals " +
                "WHERE id = ?";

        return getPeriodicalFromDb(sqlStatement, id, "id");
    }

    @Override
    public Periodical findOneByName(String name) {
        String sqlStatement = "SELECT * FROM periodicals " +
                "WHERE name = ?";

        return getPeriodicalFromDb(sqlStatement, name, "name");
    }

    private Periodical getPeriodicalFromDb(String sqlStatement, Object fieldValue,
                                           String fieldName) {
        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            switch (fieldName) {
                case "id":
                    st.setLong(1, (Long) fieldValue);
                    break;
                case "name":
                    st.setString(1, (String) fieldValue);
                    break;
                default:
                    throw new IllegalArgumentException("There is no case for such a fieldName." +
                            "Fix it!");
            }

            ResultSet rs = st.executeQuery();

            Periodical periodical = null;
            if (rs.next()) {
                periodical = getNextPeriodicalFromResults(rs);
            }

            return periodical;

        } catch (SQLException e) {
            String message = String.format("Exception during retrieving a periodical with %s = %s. " +
                    "Original: %s. ", fieldName, fieldValue, e.getMessage());
            throw new CustomSqlException(message);
        }
    }

    @Override
    public List<Periodical> findAll() {
        String sqlStatement = "SELECT * FROM periodicals";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            ResultSet rs = st.executeQuery();

            List<Periodical> periodicals = new ArrayList<>();
            Periodical periodical;
            while (rs.next()) {
                periodical = getNextPeriodicalFromResults(rs);

                periodicals.add(periodical);
            }

            return periodicals;

        } catch (SQLException e) {
            String message = String.format("Exception during retrieving all periodicals. " +
                    "Original: %s. ", e.getMessage());

            throw new CustomSqlException(message, e);
        }
    }

    private Periodical getNextPeriodicalFromResults(ResultSet rs) throws SQLException {
        Periodical periodical = new Periodical();

        periodical.setId(rs.getLong("id"));
        periodical.setName(rs.getString("name"));
        periodical.setCategory(rs.getString("category"));
        periodical.setPublisher(rs.getString("publisher"));
        periodical.setDescription(rs.getString("description"));
        periodical.setOneMonthCost(rs.getDouble("one_month_cost"));
        periodical.setStatus(Periodical.Status.valueOf(rs.getString("status").toUpperCase()));

        return periodical;
    }

    @Override
    public void createNew(Periodical periodical) {
        String sqlStatement = "INSERT INTO periodicals " +
                "(name, category, publisher, description, one_month_cost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            st.setString(1, periodical.getName());
            st.setString(2, periodical.getCategory());
            st.setString(3, periodical.getPublisher());
            st.setString(4, periodical.getDescription());
            st.setDouble(5, periodical.getOneMonthCost());
            st.setString(6, periodical.getStatus().name().toLowerCase());

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format("Exception during inserting %s into 'periodicals'. " +
                    "Original: %s. ", periodical, e.getMessage());

            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public void update(Periodical periodical) {
        String sqlStatement = "UPDATE periodicals " +
                "SET name=?, category=?, publisher=?, description=?, one_month_cost=?, status=? " +
                "WHERE name=?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);

            st.setString(1, periodical.getName());
            st.setString(2, periodical.getCategory());
            st.setString(3, periodical.getPublisher());
            st.setString(4, periodical.getDescription());
            st.setDouble(5, periodical.getOneMonthCost());
            st.setString(6, periodical.getStatus().name().toLowerCase());
            st.setString(7, periodical.getName());

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format("Exception during updating %s. " +
                    "Original: %s. ", periodical, e.getMessage());

            throw new CustomSqlException(message, e);
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
