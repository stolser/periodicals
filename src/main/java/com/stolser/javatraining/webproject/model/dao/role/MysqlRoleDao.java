package com.stolser.javatraining.webproject.model.dao.role;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MysqlRoleDao implements RoleDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlRoleDao.class);
    private Connection conn;

    public MysqlRoleDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Set<String> findRolesByUserName(String userName) {
        String sqlStatement = "SELECT user_roles.name " +
                "FROM users INNER JOIN user_roles " +
                "ON (users.id = user_roles.user_id) " +
                "INNER JOIN logins " +
                "ON (logins.user_id = users.id) " +
                "WHERE logins.user_name = ?";

        Set<String> roles = new HashSet<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                roles.add(rs.getString("user_roles.name"));
            }

        } catch (SQLException e) {
            LOGGER.error("Exception during retrieving roles for user with userName = {}",
                    userName, e);
            throw new CustomSqlException(e);
        }

        return roles;

    }
}
