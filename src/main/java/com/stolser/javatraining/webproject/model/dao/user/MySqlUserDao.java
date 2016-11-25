package com.stolser.javatraining.webproject.model.dao.user;

import java.sql.Connection;
import java.util.List;

public class MysqlUserDao<User> implements UserDao<User> {
    private Connection conn;

    public MysqlUserDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User findOne(long id) {
        String sqlStatement = "";
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
