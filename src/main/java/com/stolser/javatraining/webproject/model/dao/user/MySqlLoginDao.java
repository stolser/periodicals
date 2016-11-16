package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.entity.user.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
        long id = login.getId();
        String _login = login.getLogin();
        String passwordSalt = login.getPasswordSalt();
        String passwordHash = login.getPasswordHash();
        long userId = login.getUser().getId();
        Date registrDate = login.getRegistrationDate();

        String insertLogin = "INSERT INTO logins " +
                "(login, passwordSalt, passwordHash, userId) " +
                String.format("VALUES ('%s', '%s', '%s', '%d');",
                        _login, passwordSalt, passwordHash, userId);

        try (Statement st = conn.createStatement()) {
            st.executeUpdate(insertLogin);
        }
    }

    @Override
    public void delete(long id) throws SQLException {

    }
}
