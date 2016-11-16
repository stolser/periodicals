package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.entity.user.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private Connection conn;

    public MysqlUserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean exists(long id) throws SQLException {
        return false;
    }

    @Override
    public User findOne(long id) throws SQLException {
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public User save(User user) throws SQLException {
        return null;
    }

    @Override
    public void delete(long id) throws SQLException {

    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
