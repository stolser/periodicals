package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.periodical.PeriodicalDao;
import com.stolser.javatraining.webproject.model.dao.subscription.SubscriptionDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.statistics.PeriodicalNumberByCategory;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.model.storage.ConnectionPool;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.service.PeriodicalService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PeriodicalServiceImpl implements PeriodicalService {
    private static final String NO_PERIODICAL_WITH_ID_MESSAGE =
            "There is no periodical in the DB with id = %d";
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private PeriodicalServiceImpl() {
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

        try (Connection conn = connectionPool.getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOneById(id);

            return periodical;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Periodical findOneByName(String name) {
        try (Connection conn = connectionPool.getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            Periodical periodical = periodicalDao.findOneByName(name);

            return periodical;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Periodical> findAll() {
        try (Connection conn = connectionPool.getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            List<Periodical> periodicals = periodicalDao.findAll();

            return periodicals;

        } catch (SQLException e) {
            throw new StorageException(e);

        }
    }

    @Override
    public List<Periodical> findAllByStatus(Periodical.Status status) {
        try (Connection conn = connectionPool.getConnection()) {

            return factory.getPeriodicalDao(conn).findAllByStatus(status);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Periodical save(Periodical periodical) {
        long thisId = periodical.getId();

        try {
            if (thisId == 0) {
                createNewPeriodical(periodical);
            } else {
                tryToUpdatePeriodical(periodical);
            }

            return getPeriodicalFromDbByName(periodical.getName());

        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    private void createNewPeriodical(Periodical periodical) throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.createNew(periodical);
        }
    }

    private Periodical getPeriodicalFromDbByName(String name) throws SQLException {
        Periodical periodicalInDb;

        try (Connection conn = connectionPool.getConnection()) {
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

        try (Connection conn = connectionPool.getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalInDb = periodicalDao.findOneById(id);
        }

        return periodicalInDb;
    }

    private void updatePeriodical(Periodical periodical) throws SQLException {
        try (Connection conn = connectionPool.getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.update(periodical);
        }
    }

    @Override
    public int updateAndSetDiscarded(Periodical periodical) {
        try (Connection conn = connectionPool.getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);

            return periodicalDao.updateAndSetDiscarded(periodical);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void deleteAllDiscarded() {
        try (Connection conn = connectionPool.getConnection()) {

            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            periodicalDao.deleteAllDiscarded();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public boolean hasActiveSubscriptions(long periodicalId) {
        try (Connection conn = connectionPool.getConnection()) {

            SubscriptionDao subscriptionDao = factory.getSubscriptionDao(conn);

            return subscriptionDao.findAllByPeriodicalIdAndStatus(periodicalId,
                    Subscription.Status.ACTIVE).size() > 0;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<PeriodicalNumberByCategory> getQuantitativeStatistics() {
        List<PeriodicalNumberByCategory> statistics = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            PeriodicalDao dao = factory.getPeriodicalDao(conn);

            for (PeriodicalCategory category : PeriodicalCategory.values()) {
                int active = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                        Periodical.Status.ACTIVE);
                int inActive = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                        Periodical.Status.INACTIVE);
                int discarded = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                        Periodical.Status.DISCARDED);

                PeriodicalNumberByCategory nextItem = PeriodicalNumberByCategory.newBuilder(category)
                        .setActive(active).setInActive(inActive).setDiscarded(discarded)
                        .build();

                statistics.add(nextItem);
            }

            return statistics;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
