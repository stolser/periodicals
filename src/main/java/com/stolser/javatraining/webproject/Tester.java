package com.stolser.javatraining.webproject;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class Tester {
    public static void main(String[] args) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("connection has been got.");
            PeriodicalDao periodicalDao = DaoFactory.getMysqlDaoFactory().getPeriodicalDao(conn);

            System.out.println(periodicalDao.findOne(2L));
        } catch (SQLException e) {
            System.out.println("Exception during closing a connection.");
            throw new CustomSqlException(e);
        }
    }
}
