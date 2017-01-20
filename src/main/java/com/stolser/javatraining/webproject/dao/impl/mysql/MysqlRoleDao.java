package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.RoleDao;
import com.stolser.javatraining.webproject.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

class MysqlRoleDao implements RoleDao {
    private static final String DB_USER_ROLES_NAME = "user_roles.name";
    private static final String RETRIEVING_ROLES_FOR_USER =
            "Exception during retrieving roles for user with userName = '%s'";
    private static final String EXCEPTION_DURING_INSERTING_ROLE =
            "Exception during executing statement: '%s' for " +
            "userId = %d";
    private Connection conn;

    public MysqlRoleDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Set<String> findRolesByUserName(String userName) {
        String sqlStatement = "SELECT user_roles.name " +
                "FROM users INNER JOIN user_roles " +
                "ON (users.id = user_roles.user_id) " +
                "INNER JOIN credentials " +
                "ON (credentials.user_id = users.id) " +
                "WHERE credentials.user_name = ?";

        Set<String> roles = new HashSet<>();

        try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setString(1, userName);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getString(DB_USER_ROLES_NAME));
                }
            }

        } catch (SQLException e) {
            String message = String.format(RETRIEVING_ROLES_FOR_USER, userName);
            throw new DaoException(message, e);
        }

        return roles;
    }

    @Override
    public void addRole(long userId, String roleName) {
        String sqlStatement = "INSERT INTO user_roles " +
                "(user_id, name) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setLong(1, userId);
            st.setString(2, roleName);

            st.executeUpdate();

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_INSERTING_ROLE, sqlStatement, userId);
            throw new DaoException(message, e);
        }
    }
}
