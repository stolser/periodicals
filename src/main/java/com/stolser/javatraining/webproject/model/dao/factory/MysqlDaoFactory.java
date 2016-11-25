package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.periodical.MysqlPeriodicalDao;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;
import com.stolser.javatraining.webproject.model.dao.role.MysqlRoleDao;
import com.stolser.javatraining.webproject.model.dao.role.RoleDao;
import com.stolser.javatraining.webproject.model.dao.user.MysqlUserDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;

import java.sql.Connection;

public class MysqlDaoFactory extends DaoFactory {

    @Override
    public PeriodicalDao getPeriodicalDao(Connection conn) {
        return new MysqlPeriodicalDao(conn);
    }

    @Override
    public UserDao getUserDao(Connection conn) {
        return new MysqlUserDao(conn);
    }

    @Override
    public RoleDao getRoleDao(Connection conn) {
        return new MysqlRoleDao(conn);
    }
}
