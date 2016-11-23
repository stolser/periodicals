package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.user.UserDao;

import java.sql.Connection;

public abstract class DaoFactory {
    public abstract UserDao getUserDao(Connection conn);

    public static DaoFactory getMysqlDaoFactory() {
        return new MysqlDaoFactory();
    }
}
