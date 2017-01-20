package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.PeriodicalDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.statistics.PeriodicalNumberByCategory;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.service.PeriodicalService;

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

    public static PeriodicalService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Periodical findOneById(long id) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            PeriodicalDao periodicalDao = factory.getPeriodicalDao(conn);
            return periodicalDao.findOneById(id);
        }
    }

    @Override
    public Periodical findOneByName(String name) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).findOneByName(name);
        }
    }

    @Override
    public List<Periodical> findAll() {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).findAll();
        }
    }

    @Override
    public List<Periodical> findAllByStatus(Periodical.Status status) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).findAllByStatus(status);
        }
    }

    @Override
    public Periodical save(Periodical periodical) {
        if (periodical.getId() == 0) {
            createNewPeriodical(periodical);
        } else {
            tryToUpdatePeriodical(periodical);
        }

        return getPeriodicalFromDbByName(periodical.getName());
    }

    private void createNewPeriodical(Periodical periodical) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            factory.getPeriodicalDao(conn).createNew(periodical);
        }
    }

    private Periodical getPeriodicalFromDbByName(String name) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).findOneByName(name);
        }
    }

    private void tryToUpdatePeriodical(Periodical periodical) {
        Periodical periodicalInDb = getPeriodicalFromDbById(periodical.getId());
        if (periodicalInDb == null) {
            throw new NoSuchElementException(
                    String.format(NO_PERIODICAL_WITH_ID_MESSAGE, periodical.getId()));
        }

        updatePeriodical(periodical);
    }

    private Periodical getPeriodicalFromDbById(long id) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).findOneById(id);
        }
    }

    private void updatePeriodical(Periodical periodical) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            factory.getPeriodicalDao(conn).update(periodical);
        }
    }

    @Override
    public int updateAndSetDiscarded(Periodical periodical) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).updateAndSetDiscarded(periodical);
        }
    }

    @Override
    public int deleteAllDiscarded() {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getPeriodicalDao(conn).deleteAllDiscarded();
        }
    }

    @Override
    public boolean hasActiveSubscriptions(long periodicalId) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return !factory.getSubscriptionDao(conn)
                    .findAllByPeriodicalIdAndStatus(periodicalId, Subscription.Status.ACTIVE)
                    .isEmpty();
        }
    }

    @Override
    public List<PeriodicalNumberByCategory> getQuantitativeStatistics() {
        List<PeriodicalNumberByCategory> statistics = new ArrayList<>();

        try (AbstractConnection conn = connectionPool.getConnection()) {
            PeriodicalDao dao = factory.getPeriodicalDao(conn);

            for (PeriodicalCategory category : PeriodicalCategory.values()) {
                statistics.add(getPeriodicalNumberByCategory(dao, category));
            }

            return statistics;
        }
    }

    private PeriodicalNumberByCategory getPeriodicalNumberByCategory(PeriodicalDao dao,
                                                                     PeriodicalCategory category) {
        int active = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                Periodical.Status.ACTIVE);
        int inActive = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                Periodical.Status.INACTIVE);
        int discarded = dao.findNumberOfPeriodicalsWithCategoryAndStatus(category,
                Periodical.Status.DISCARDED);

        return PeriodicalNumberByCategory.newBuilder(category)
                .setActive(active)
                .setInActive(inActive)
                .setDiscarded(discarded)
                .build();
    }
}
