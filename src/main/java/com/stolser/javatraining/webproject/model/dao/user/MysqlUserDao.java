package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlUserDao.class);
    private Connection conn;

    public MysqlUserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User findOneById(long id) {
        String sqlStatement = "SELECT * FROM users " +
                "JOIN credentials ON (users.id = credentials.user_id) " +
                "WHERE users.id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            User user = null;
            if (rs.next()) {
                user = getUserFromResults(rs);
            }

            return user;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public User findUserByUserName(String userName) {
        String sqlStatement = "SELECT * FROM credentials " +
                "INNER JOIN users ON (credentials.user_id = users.id) " +
                "WHERE credentials.user_name = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            User user = null;
            if (rs.next()) {
                user = getUserFromResults(rs);
            }

            return user;

        } catch (SQLException e) {
            LOGGER.error("Exception during retrieving a user with userName = {}", userName, e);
            throw new CustomSqlException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sqlStatement = "SELECT * FROM credentials " +
                "RIGHT OUTER JOIN users ON (credentials.user_id = users.id) ";

        try {
            ResultSet rs = conn.createStatement().executeQuery(sqlStatement);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUserFromResults(rs));
            }

            return users;

        } catch (SQLException e) {
            LOGGER.error("Exception during retrieving all users", e);
            throw new CustomSqlException(e);
        }
    }

    private User getUserFromResults(ResultSet rs) throws SQLException {
        User user;

        user = new User();
        user.setId(rs.getLong("users.id"));
        user.setUserName(rs.getString("credentials.user_name"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setBirthday(new Date(rs.getDate("birthday").getTime()));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setStatus(User.Status.valueOf(rs.getString("status").toUpperCase()));

        return user;
    }

    @Override
    public void createNew(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User entity) {
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
