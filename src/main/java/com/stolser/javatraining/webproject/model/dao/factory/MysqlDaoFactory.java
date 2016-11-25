package com.stolser.javatraining.webproject.model.dao.factory;

import com.stolser.javatraining.webproject.model.dao.periodical.MysqlPeriodicalDao;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;

import java.sql.Connection;

public class MysqlDaoFactory extends DaoFactory {

    @Override
    public PeriodicalDao getPeriodicalDao(Connection conn) {

        return new MysqlPeriodicalDao(conn);
    }
}
