package com.stolser.javatraining.webproject.model.service.periodical;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class PeriodicalService {
    private final DaoFactory factory;
    public static final String NO_PERIODICAL_WITH_ID_MESSAGE = "There is no periodical in the DB with id = %d";

    private PeriodicalService() {
        factory = DaoFactory.getMysqlDaoFactory();
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


    public Periodical findOneById(long id) {

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("PeriodicalService: connection has been got.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOneById(id);

            System.out.println("periodical from the DB: " + periodical);

//            if (periodical == null) {
//                String message = String.format(NO_PERIODICAL_WITH_ID_MESSAGE, id);
//
//                throw new NoSuchElementException(message);
//            }


            return periodical;
        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    public Periodical findOneByName(String name) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOneByName(name);

            System.out.println("periodical from the DB: " + periodical);

            return periodical;
        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    public List<Periodical> findAll() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            List<Periodical> periodicals = periodicalDao.findAll();

            return periodicals;

        } catch (SQLException e) {
            throw new CustomSqlException(e);

        }
    }

    /**
     * If the id of this periodical is 0, creates a new one. Otherwise tries to update an existing periodical in
     * the db with this id.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param periodical the persisted periodical
     * @return a periodical from the db
     */
    public Periodical save(Periodical periodical) {
        long thisId = periodical.getId();

        System.out.println("Saving a periodical: " + periodical);

        try {
            if (thisId == 0) {
                createNewPeriodical(periodical);
            } else {
                tryToUpdatePeriodical(periodical);
            }

            return getPeriodicalFromDbByName(periodical.getName());

        } catch (SQLException e) {
            throw new CustomSqlException(e);

        }

    }

    private void createNewPeriodical(Periodical periodical) throws SQLException {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("PeriodicalService: connection has been got. Creating a new one.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.createNew(periodical);
        }
    }

    private Periodical getPeriodicalFromDbByName(String name) throws SQLException {
        Periodical periodicalInDb;

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalInDb = periodicalDao.findOneByName(name);
        }

        return periodicalInDb;
    }


    private void tryToUpdatePeriodical(Periodical periodical) throws SQLException {
        Periodical periodicalInDb = getPeriodicalFromDbById(periodical.getId());

        if (periodicalInDb == null) {
            String message = String.format(NO_PERIODICAL_WITH_ID_MESSAGE, periodical.getId());

            throw new NoSuchElementException(message);
        } else {
            updatePeriodical(periodical);
        }
    }

    private Periodical getPeriodicalFromDbById(long id) throws SQLException {
        Periodical periodicalInDb;

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalInDb = periodicalDao.findOneById(id);
        }

        return periodicalInDb;
    }

    private void updatePeriodical(Periodical periodical) throws SQLException {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("PeriodicalService.updatePeriodical(): connection has been got.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.update(periodical);
        }
    }

    public void deleteAllDiscarded() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.deleteAllDiscarded();
        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    public boolean hasActiveSubscriptions(long periodicalId) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);

            return subscriptionDao.findAllByPeriodicalIdAndStatus(periodicalId,
                    Subscription.Status.ACTIVE).size() > 0;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

}
