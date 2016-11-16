package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.user.LoginDao;
import com.stolser.javatraining.webproject.model.dao.user.MysqlLoginDao;
import com.stolser.javatraining.webproject.model.dao.user.MysqlUserDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;

import java.sql.Connection;

public class MysqlDaoFactory extends DaoFactory {

    @Override
    public LoginDao getLoginDao(Connection conn) {
        return new MysqlLoginDao(conn);
    }

    @Override
    public UserDao getUserDao(Connection conn) {
        return new MysqlUserDao(conn);
    }
}
