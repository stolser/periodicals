package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class DaoUtils {

    public static Periodical getPeriodicalFromResultSet(ResultSet rs) throws SQLException {
        Periodical.Builder periodicalBuilder = new Periodical.Builder();
        periodicalBuilder.setId(rs.getLong(DB_PERIODICALS_ID))
                .setName(rs.getString(DB_PERIODICALS_NAME))
                .setCategory(PeriodicalCategory.valueOf(
                        rs.getString(DB_PERIODICALS_CATEGORY).toUpperCase()))
                .setPublisher(rs.getString(DB_PERIODICALS_PUBLISHER))
                .setDescription(rs.getString(DB_PERIODICALS_DESCRIPTION))
                .setOneMonthCost(rs.getLong(DB_PERIODICALS_ONE_MONTH_COST))
                .setStatus(Periodical.Status.valueOf(
                        rs.getString(DB_PERIODICALS_STATUS).toUpperCase()));

        return periodicalBuilder.build();
    }
}
