package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class MysqlUserDao implements UserDao {
    private static final String EXCEPTION_DURING_FINDING_ALL_USERS = "Exception during finding all users.";
    private static final String EXCEPTION_DURING_FINDING_USER_BY_NAME =
            "Exception during finding a user with userName = %s.";
    private static final String EXCEPTION_DURING_FINDING_USER_BY_ID =
            "Exception during finding a user with id = %d.";
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

            return rs.next() ? getUserFromResults(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_USER_BY_ID, id);

            throw new StorageException(message, e);
        }
    }

    @Override
    public User findOneByUserName(String userName) {
        String sqlStatement = "SELECT * FROM credentials " +
                "INNER JOIN users ON (credentials.user_id = users.id) " +
                "WHERE credentials.user_name = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            return rs.next() ? getUserFromResults(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_USER_BY_NAME, userName);

            throw new StorageException(message, e);
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
            String message = String.format(EXCEPTION_DURING_FINDING_ALL_USERS);

            throw new StorageException(message, e);
        }
    }

    private User getUserFromResults(ResultSet rs) throws SQLException {
        User user;

        user = new User();
        user.setId(rs.getLong(DB_USERS_ID));
        user.setUserName(rs.getString(DB_CREDENTIALS_USER_NAME));
        user.setFirstName(rs.getString(DB_USERS_FIRST_NAME));
        user.setLastName(rs.getString(DB_USERS_LAST_NAME));
        user.setBirthday(new Date(rs.getDate(DB_USERS_BIRTHDAY).getTime()));
        user.setEmail(rs.getString(DB_USERS_EMAIL));
        user.setAddress(rs.getString(DB_USERS_ADDRESS));
        user.setStatus(User.Status.valueOf(rs.getString(DB_USERS_STATUS).toUpperCase()));

        return user;
    }

    @Override
    public void createNew(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(User entity) {
        throw new UnsupportedOperationException();
    }
}
