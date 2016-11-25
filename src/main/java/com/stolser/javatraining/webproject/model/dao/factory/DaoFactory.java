package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;

import java.sql.Connection;

public abstract class DaoFactory {
    public abstract PeriodicalDao getPeriodicalDao(Connection conn);

    public static DaoFactory getMysqlDaoFactory() {
        return new MysqlDaoFactory();
    }
}
