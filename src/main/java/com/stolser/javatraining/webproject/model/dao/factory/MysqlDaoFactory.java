package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.user.MysqlUserDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;

import java.sql.Connection;

public class MysqlDaoFactory extends DaoFactory {

    @Override
    public UserDao getUserDao(Connection conn) {
        return new MysqlUserDao(conn);
    }
}
