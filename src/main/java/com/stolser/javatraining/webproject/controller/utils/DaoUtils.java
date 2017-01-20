package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.dao.impl.mysql.MysqlPeriodicalDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DaoUtils {
    private DaoUtils() {}

    /**
     * Creates a new periodical using the data from the result set.
     */
    public static Periodical getPeriodicalFromResultSet(ResultSet rs) throws SQLException {
        Periodical.Builder periodicalBuilder = new Periodical.Builder();
        periodicalBuilder.setId(rs.getLong(MysqlPeriodicalDao.DB_PERIODICALS_ID))
                .setName(rs.getString(MysqlPeriodicalDao.DB_PERIODICALS_NAME))
                .setCategory(PeriodicalCategory.valueOf(
                        rs.getString(MysqlPeriodicalDao.DB_PERIODICALS_CATEGORY).toUpperCase()))
                .setPublisher(rs.getString(MysqlPeriodicalDao.DB_PERIODICALS_PUBLISHER))
                .setDescription(rs.getString(MysqlPeriodicalDao.DB_PERIODICALS_DESCRIPTION))
                .setOneMonthCost(rs.getLong(MysqlPeriodicalDao.DB_PERIODICALS_ONE_MONTH_COST))
                .setStatus(Periodical.Status.valueOf(
                        rs.getString(MysqlPeriodicalDao.DB_PERIODICALS_STATUS).toUpperCase()));

        return periodicalBuilder.build();
    }
}
