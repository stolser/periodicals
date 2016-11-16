package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.entity.user.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlLoginDao implements LoginDao {
    private Connection conn;

    public MysqlLoginDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean exists(String login) throws SQLException {
        return false;
    }

    @Override
    public Login findOne(long id) throws SQLException {
        return null;
    }

    @Override
    public void save(Login login) throws SQLException {
        String insertLogin = "INSERT INTO logins (login, passwordSalt, passwordHash, userId, registrDate) " +
                "VALUES (?,?,?,?,?);";

        try (PreparedStatement st = conn.prepareStatement(insertLogin)) {
            st.setString(1, login.getLogin());
            st.setString(2, login.getPasswordSalt());
            st.setString(3, login.getPasswordHash());
            st.setLong(4, login.getUser().getId());
            st.setTimestamp(5, new java.sql.Timestamp(login.getRegistrationDate().getTime()));

            st.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {

    }
}
