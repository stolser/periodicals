package com.stolser.javatraining.webproject.model.service.periodical;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class PeriodicalService {
    private static final DaoFactory factory = DaoFactory.getMysqlDaoFactory();

    private PeriodicalService() {
    }

    private static class InstanceHolder {
        private static final PeriodicalService INSTANCE = new PeriodicalService();
    }

    /**
     * @return a singleton object of this type
     */
    public static PeriodicalService getInstance() {
        return InstanceHolder.INSTANCE;
    }


    public Periodical findOne(long id) {

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("PeriodicalService: connection has been got.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOne(id);

            if (periodical == null) {
                String message = String.format("There is no periodical in the DB with id = %d", id);

                throw new NoSuchElementException(message);
            }

            System.out.println(periodical);

            return periodical;
        } catch (SQLException e) {
            String message = String.format("Exception during closing a connection. " +
                    "Original: $s. ", e.getMessage());
            throw new CustomSqlException(message);
        }
    }

    public List<Periodical> findAll() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            List<Periodical> periodicals = periodicalDao.findAll();

            return periodicals;

        } catch (SQLException e) {
            String message = String.format("Exception during closing a connection. " +
                    "Original: $s. ", e.getMessage());
            throw new CustomSqlException(message);
        }
    }

    public Periodical save(Periodical entity) {
        return null;
    }

    public void delete(long id) {

    }

    public void deleteAll() {

    }
}
