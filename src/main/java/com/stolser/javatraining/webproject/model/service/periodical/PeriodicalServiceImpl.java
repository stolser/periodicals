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

public class PeriodicalServiceImpl implements PeriodicalService {
    private final DaoFactory factory;
    public static final String NO_PERIODICAL_WITH_ID_MESSAGE = "There is no periodical in the DB with id = %d";

    private PeriodicalServiceImpl() {
        factory = DaoFactory.getMysqlDaoFactory();
    }

    private static class InstanceHolder {
        private static final PeriodicalServiceImpl INSTANCE = new PeriodicalServiceImpl();
    }

    /**
     * @return a singleton object of this type
     */
    public static PeriodicalService getInstance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public Periodical findOneById(long id) {

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("PeriodicalServiceImpl: connection has been got.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOneById(id);

            System.out.println("periodical from the DB: " + periodical);

            return periodical;
        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
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

    @Override
    public List<Periodical> findAll() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            List<Periodical> periodicals = periodicalDao.findAll();

            return periodicals;

        } catch (SQLException e) {
            throw new CustomSqlException(e);

        }
    }

    @Override
    public List<Periodical> findAllByStatus(Periodical.Status status) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            return factory.getPeriodicalDao(conn).findAllByStatus(status);

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    /**
     * If the id of this periodical is 0, creates a new one. Otherwise tries to update an existing periodical in
     * the db with this id.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @param periodical the persisted periodical
     * @return a periodical from the db
     */
    @Override
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
            System.out.println("PeriodicalServiceImpl: connection has been got. Creating a new one.");

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
            System.out.println("PeriodicalServiceImpl.updatePeriodical(): connection has been got.");

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.update(periodical);
        }
    }

    @Override
    public void deleteAllDiscarded() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.deleteAllDiscarded();
        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public boolean hasActiveSubscriptions(long periodicalId) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);

            return subscriptionDao.findAllByPeriodicalIdAndStatus(periodicalId,
                    Subscription.Status.ACTIVE).size() > 0;

        } catch (SQLException e) {
            throw new CustomSqlException(e);
        }
    }

    @Override
    public boolean discard(Periodical periodicalToDiscard) {
        Connection conn = ConnectionPoolProvider.getPool().getConnection();
        int oldIsolationLevel = Connection.TRANSACTION_READ_COMMITTED;

        try {
            oldIsolationLevel = conn.getTransactionIsolation();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            if (!hasActiveSubscriptions(periodicalToDiscard.getId())) {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                save(periodicalToDiscard);
                conn.commit();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (SQLException e1) {
                throw new CustomSqlException(e);
            }

            throw new CustomSqlException(e);
        } finally {
            if (conn != null) {
                try {
                    // todo: is it necessary?
                    conn.setAutoCommit(true);
                    conn.setTransactionIsolation(oldIsolationLevel);
                    conn.close();

                } catch (SQLException e) {
                    throw new CustomSqlException(e);
                }
            }
        }
    }

}
