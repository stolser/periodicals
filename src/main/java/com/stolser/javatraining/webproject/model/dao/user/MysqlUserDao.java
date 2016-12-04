package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlUserDao.class);
    private Connection conn;

    public MysqlUserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User findOneById(long id) {
        String sqlStatement = "";
        return null;
    }

    @Override
    public User findUserByUserName(String userName) {
        String sqlStatement = "SELECT * FROM logins " +
                "INNER JOIN users ON (logins.user_id = users.id) " +
                "WHERE logins.user_name = ?";

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
            LOGGER.debug("Exception during retrieving a user with userName = {}", userName, e);
            throw new CustomSqlException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sqlStatement = "SELECT * FROM logins " +
                "RIGHT OUTER JOIN users ON (logins.user_id = users.id) ";

        try {
            ResultSet rs = conn.createStatement().executeQuery(sqlStatement);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUserFromResults(rs));
            }

            return users;

        } catch (SQLException e) {
            LOGGER.debug("Exception during retrieving all users", e);
            throw new CustomSqlException(e);
        }
    }

    private User getUserFromResults(ResultSet rs) throws SQLException {
        User user;

        user = new User();
        user.setId(rs.getLong("users.id"));
        user.setUserName(rs.getString("logins.user_name"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setStatus(User.Status.valueOf(rs.getString("status").toUpperCase()));

        return user;
    }

    @Override
    public void createNew(User entity) {
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
