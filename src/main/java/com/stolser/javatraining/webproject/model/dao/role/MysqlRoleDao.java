package com.stolser.javatraining.webproject.model.dao.role;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.storage.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MysqlRoleDao implements RoleDao {
    private static final String RETRIEVING_ROLES_FOR_USER = "Exception during retrieving roles for user with userName = '%s'";
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

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                roles.add(rs.getString(ApplicationResources.DB_USER_ROLES_NAME));
            }

        } catch (SQLException e) {
            String message = String.format(RETRIEVING_ROLES_FOR_USER,
                    userName);
            
            throw new StorageException(message, e);
        }

        return roles;
    }

    @Override
    public void addRole(long userId, String roleName) {
        String sqlStatement = "INSERT INTO user_roles " +
                "(user_id, name) VALUES (?, ?)";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, userId);
            st.setString(2, roleName);

            st.executeUpdate();

        } catch (SQLException e) {

            throw new StorageException(e);
        }
    }
}
