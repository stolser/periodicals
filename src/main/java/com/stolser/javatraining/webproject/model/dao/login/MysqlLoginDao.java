package com.stolser.javatraining.webproject.model.dao.login;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.user.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlLoginDao implements LoginDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlLoginDao.class);
    private Connection conn;

    public MysqlLoginDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Login findLoginByUserName(String userName) {
        String sqlStatement = "SELECT * FROM logins " +
                "WHERE user_name = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            Login login = null;
            if (rs.next()) {
                login = new Login();
                login.setId(rs.getLong("id"));
                login.setUserName(rs.getString("user_name"));
                login.setPasswordHash(rs.getString("password_hash"));
            }

            return login;

        } catch (SQLException e) {
            LOGGER.error("Exception during retrieving a login with userName = {}", userName, e);
            throw new CustomSqlException(e);
        }
    }

}
