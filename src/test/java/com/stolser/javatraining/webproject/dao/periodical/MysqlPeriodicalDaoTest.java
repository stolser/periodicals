package com.stolser.javatraining.webproject.dao.periodical;

import com.stolser.javatraining.webproject.connection.pool.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.PeriodicalDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MysqlPeriodicalDaoTest {
    private static final int PERIODICAL_ID = 1;
    private static final String PERIODICAL_NAME = "The New York Times";
    private static final String NEW_PERIODICAL_NAME = "A new Periodical";
    private static final String NEW_PUBLISHER_NAME = "Test Publisher";
    private static final int ONE_MONTH_COST = 22;
    private static final int PERIODICAL_TO_UPDATE_ID = 32;
    private static PeriodicalDao periodicalDao;
    private static AbstractConnection conn;
    private static DaoFactory factory;
    private static Periodical expected;

    @BeforeClass
    public static void setUp() throws Exception {
        conn = ConnectionPoolProvider.getTestPool().getConnection();
        factory = DaoFactory.getMysqlDaoFactory();
        periodicalDao = factory.getPeriodicalDao(conn);

        Periodical.Builder periodicalBuilder = new Periodical.Builder();
        periodicalBuilder.setName(PERIODICAL_NAME)
                .setCategory(PeriodicalCategory.NEWS)
                .setOneMonthCost(ONE_MONTH_COST)
                .setStatus(Periodical.Status.INACTIVE);

        expected = periodicalBuilder.build();
    }

    @Test
    public void findOneById_Should_ReturnOnePeriodical() throws Exception {
        assertPeriodicalData(expected, periodicalDao.findOneById(PERIODICAL_ID));
    }

    private void assertPeriodicalData(Periodical expected, Periodical actual) {
        assertEquals("Name", expected.getName(), actual.getName());
        assertEquals("Category", expected.getCategory(), actual.getCategory());
        assertEquals("OneMonthCost", expected.getOneMonthCost(), actual.getOneMonthCost());
        assertEquals("Status", expected.getStatus(), actual.getStatus());
    }

    @Test
    public void findOneByName_Should_ReturnOnePeriodical() throws Exception {
        assertPeriodicalData(expected, periodicalDao.findOneByName(PERIODICAL_NAME));
    }

    @Test
    public void findAll_Should_ReturnCorrectValues() throws Exception {
        int expectedNumber = 8;
        int actualNumber = periodicalDao.findAll().size();

        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void findAllByStatus_Should_ReturnCorrectValues() throws Exception {
        int activeExpectedNumber = 5;
        int inActiveExpectedNumber = 2;
        int discardedExpectedNumber = 1;

        int activeActualNumber = periodicalDao.findAllByStatus(Periodical.Status.ACTIVE).size();
        int inActiveActualNumber = periodicalDao.findAllByStatus(Periodical.Status.INACTIVE).size();
        int discardedActualNumber = periodicalDao.findAllByStatus(Periodical.Status.DISCARDED).size();

        assertEquals("active periodicals", activeExpectedNumber, activeActualNumber);
        assertEquals("inActive periodicals", inActiveExpectedNumber, inActiveActualNumber);
        assertEquals("discarded periodicals", discardedExpectedNumber, discardedActualNumber);
    }

    @Test
    public void findNumberOfPeriodicalsWithCategoryAndStatus_Should_ReturnCorrectValues() throws Exception {
        int activeNewsExpectedNumber = 2;
        int inActiveNewsExpectedNumber = 2;
        int discardedNewsExpectedNumber = 1;

        int activeNewsActualNumber = periodicalDao.findNumberOfPeriodicalsWithCategoryAndStatus(
                PeriodicalCategory.NEWS, Periodical.Status.ACTIVE);
        int inActiveNewsActualNumber = periodicalDao.findNumberOfPeriodicalsWithCategoryAndStatus(
                PeriodicalCategory.NEWS, Periodical.Status.INACTIVE);
        int discardedNewsActualNumber = periodicalDao.findNumberOfPeriodicalsWithCategoryAndStatus(
                PeriodicalCategory.NEWS, Periodical.Status.DISCARDED);

        assertEquals("activeNews periodicals", activeNewsExpectedNumber, activeNewsActualNumber);
        assertEquals("inActiveNews periodicals", inActiveNewsExpectedNumber, inActiveNewsActualNumber);
        assertEquals("discardedNews periodicals", discardedNewsExpectedNumber, discardedNewsActualNumber);
    }

    @Ignore(value = "change the status of the db")
    @Test
    public void createNew_Should_IncreaseNumberOfPeriodicalsInDb_ByOne() throws Exception {
        int numberBefore = periodicalDao.findAll().size();

        Periodical.Builder periodicalBuilder = new Periodical.Builder();
        periodicalBuilder.setName(NEW_PERIODICAL_NAME)
                .setCategory(PeriodicalCategory.NEWS)
                .setPublisher(NEW_PUBLISHER_NAME)
                .setOneMonthCost(ONE_MONTH_COST)
                .setStatus(Periodical.Status.ACTIVE);
        Periodical newPeriodical = periodicalBuilder.build();

        periodicalDao.createNew(newPeriodical);

        int numberAfter = periodicalDao.findAll().size();
        assertEquals(numberBefore, numberAfter - 1);

        Periodical periodicalFromDb = periodicalDao.findOneByName(NEW_PERIODICAL_NAME);

        assertPeriodicalData(newPeriodical, periodicalFromDb);
    }
}