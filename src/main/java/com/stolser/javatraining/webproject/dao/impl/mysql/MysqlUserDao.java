package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.UserDao;
import com.stolser.javatraining.webproject.dao.exception.DaoException;
import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

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
    private static final String CREATING_USER_FAILED_NO_ROWS_AFFECTED = "Creating user (%s) failed, no rows affected.";
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

            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? getUserFromResults(rs) : null;
            }

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

            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? getUserFromResults(rs) : null;
            }

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_FINDING_USER_BY_NAME, userName);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean emailExistsInDb(String email) {
        String sqlStatement = "SELECT COUNT(id) FROM users " +
                "WHERE users.email = ?";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setString(1, email);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sqlStatement = "SELECT * FROM credentials " +
                "RIGHT OUTER JOIN users ON (credentials.user_id = users.id) ";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement);
             ResultSet rs = st.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUserFromResults(rs));
            }

            return users;

        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_DURING_FINDING_ALL_USERS, e);
        }
    }

    private User getUserFromResults(ResultSet rs) throws SQLException {
        User.Builder builder = new User.Builder();

        builder.setId(rs.getLong(DB_USERS_ID))
                .setUserName(rs.getString(MysqlCredentialDao.DB_CREDENTIALS_USER_NAME))
                .setFirstName(rs.getString(DB_USERS_FIRST_NAME))
                .setLastName(rs.getString(DB_USERS_LAST_NAME))
                .setBirthday(getBirthdayFromRs(rs))
                .setEmail(rs.getString(DB_USERS_EMAIL))
                .setAddress(rs.getString(DB_USERS_ADDRESS))
                .setStatus(User.Status.valueOf(rs.getString(DB_USERS_STATUS).toUpperCase()));

        return builder.build();
    }

    private Date getBirthdayFromRs(ResultSet rs) throws SQLException {
        java.sql.Date birthday = rs.getDate(DB_USERS_BIRTHDAY);
        return nonNull(birthday) ? new Date(birthday.getTime()) : null;
    }

    @Override
    public long createNew(User user) {
        String errorMessage = String.format(EXCEPTION_DURING_CREATING_NEW_USER, user);
        String errorMessageNoRows = String.format(CREATING_USER_FAILED_NO_ROWS_AFFECTED, user);
        String sqlStatement = "INSERT INTO users " +
                "(first_name, last_name, birthday, email, address, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setDate(3, getBirthdayFromUser(user));
            st.setString(4, user.getEmail());
            st.setString(5, user.getAddress());
            st.setString(6, user.getStatus().name().toLowerCase());

            tryExecuteUpdate(st, errorMessage);

            return tryRetrieveId(st, errorMessageNoRows);

        } catch (SQLException e) {
            throw new DaoException(errorMessage, e);
        }
    }

    private void tryExecuteUpdate(PreparedStatement st, String errorMessage) throws SQLException {
        int affectedRows = st.executeUpdate();
        if (affectedRows == 0) {
            throw new DaoException(errorMessage);
        }
    }

    private long tryRetrieveId(PreparedStatement st, String errorMessageNoRows) throws SQLException {
        try (ResultSet generatedKeys = st.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new DaoException(errorMessageNoRows);
            }
        }
    }

    private java.sql.Date getBirthdayFromUser(User user) {
        Date birthday = user.getBirthday();
        return nonNull(birthday) ? new java.sql.Date(birthday.getTime()) : null;
    }

    @Override
    public int update(User entity) {
        throw new UnsupportedOperationException();
    }
}
