package com.stolser.javatraining.webproject.model.dao.periodical;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlPeriodicalDao implements PeriodicalDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlPeriodicalDao.class);
    private Connection conn;

    public MysqlPeriodicalDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Periodical findOne(long id) {
        String sqlStatement = "SELECT * FROM periodicals WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            Periodical periodical = null;
            if (rs.next()) {
                periodical = new Periodical();
                periodical.setId(rs.getLong("id"));
                periodical.setName(rs.getString("name"));
                periodical.setCategory(rs.getString("category"));
                periodical.setPublisher(rs.getString("publisher"));
                periodical.setDescription(rs.getString("description"));
                periodical.setOneMonthCost(rs.getDouble("oneMonthCost"));
            }

            return periodical;

        } catch (SQLException e) {
            LOGGER.debug("Exception during retrieving a periodical with id = {}", id, e);
            throw new CustomSqlException(e);
        }

    }

    @Override
    public List<Periodical> findAll() {
        return null;
    }

    @Override
    public Periodical save(Periodical entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }
}
