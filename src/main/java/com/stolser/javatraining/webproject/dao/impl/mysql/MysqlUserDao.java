package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.exception.DaoException;
import com.stolser.javatraining.webproject.dao.UserDao;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class MysqlUserDao implements UserDao {
    private static final String DB_USERS_ID = "users.id";
    private static final String DB_USERS_FIRST_NAME = "users.first_name";
    private static final String DB_USERS_LAST_NAME = "users.last_name";
    private static final String DB_USERS_BIRTHDAY = "users.birthday";
    private static final String DB_USERS_EMAIL = "users.email";
    private static final String DB_USERS_ADDRESS = "users.address";
    private static final String DB_USERS_STATUS = "users.status";
    private static final String EXCEPTION_DURING_FINDING_ALL_USERS = "Exception during finding all users.";
    private static final String EXCEPTION_DURING_FINDING_USER_BY_NAME =
            "Exception during finding a user with userName = %s.";
    private static final String EXCEPTION_DURING_FINDING_USER_BY_ID =
            "Exception during finding a user with id = %d.";
    private static final String EXCEPTION_DURING_CREATING_NEW_USER = "Exception during creating a new user: %s";
    private Connection conn;

    public MysqlUserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User findOneById(long id) {
        String sqlStatement = "SELECT * FROM users " +
                "JOIN credentials ON (users.id = credentials.user_id) " +
                "WHERE users.id = ?";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            return rs.next() ? getUserFromResults(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_USER_BY_ID, id);
            throw new DaoException(message, e);
        }
    }

    @Override
    public User findOneByUserName(String userName) {
        String sqlStatement = "SELECT * FROM credentials " +
                "INNER JOIN users ON (credentials.user_id = users.id) " +
                "WHERE credentials.user_name = ?";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            return rs.next() ? getUserFromResults(rs) : null;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_USER_BY_NAME, userName);
            throw new DaoException(message, e);
        }
    }

    @Override
    public List<User> findAll() {
        String sqlStatement = "SELECT * FROM credentials " +
                "RIGHT OUTER JOIN users ON (credentials.user_id = users.id) ";

        try (ResultSet rs = conn.createStatement().executeQuery(sqlStatement)) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUserFromResults(rs));
            }

            return users;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_ALL_USERS);
            throw new DaoException(message, e);
        }
    }

    private User getUserFromResults(ResultSet rs) throws SQLException {
        User.Builder builder = new User.Builder();

        builder.setId(rs.getLong(DB_USERS_ID))
                .setUserName(rs.getString(MysqlCredentialDao.DB_CREDENTIALS_USER_NAME))
                .setFirstName(rs.getString(DB_USERS_FIRST_NAME))
                .setLastName(rs.getString(DB_USERS_LAST_NAME))
                .setBirthday(new Date(rs.getDate(DB_USERS_BIRTHDAY).getTime()))
                .setEmail(rs.getString(DB_USERS_EMAIL))
                .setAddress(rs.getString(DB_USERS_ADDRESS))
                .setStatus(User.Status.valueOf(rs.getString(DB_USERS_STATUS).toUpperCase()));

        return builder.build();
    }

    @Override
    public long createNew(User user) {

        String sqlStatement = "INSERT INTO users " +
                "(first_name, last_name, birthday, email, address, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
            st.setString(4, user.getEmail());
            st.setString(5, user.getAddress());
            st.setString(6, user.getStatus().name().toLowerCase());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            return rs.getLong("id");

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_CREATING_NEW_USER, user);
            throw new DaoException(message, e);
        }
    }

    @Override
    public int update(User entity) {
        throw new UnsupportedOperationException();
    }
}
