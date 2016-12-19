package com.stolser.javatraining.webproject.model.dao.periodical;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class MysqlPeriodicalDaoTest {
    private static final int PERIODICAL_ID = 1;
    private static final String PERIODICAL_NAME = "The New York Times";
    private static final String NEW_PERIODICAL_NAME = "A new Periodical";
    private static final String NEW_PUBLISHER_NAME = "Test Publisher";
    private static final int ONE_MONTH_COST = 22;
    private static PeriodicalDao periodicalDao;
    private static Connection conn;
    private static DaoFactory factory;

    @BeforeClass
    public static void setUp() throws Exception {
        conn = ConnectionPoolProvider.getTestPool().getConnection();
        factory = DaoFactory.getMysqlDaoFactory();
        periodicalDao = factory.getPeriodicalDao(conn);

    }

    @Test
    public void findOneById_Should_ReturnOnePeriodical() throws Exception {
        Periodical expected = new Periodical();
        expected.setName(PERIODICAL_NAME);
        expected.setCategory(PeriodicalCategory.NEWS);
        expected.setOneMonthCost(22);
        expected.setStatus(Periodical.Status.INACTIVE);

        assertPeriodicalData(expected, periodicalDao.findOneById(PERIODICAL_ID));
    }

    private void assertPeriodicalData(Periodical expected, Periodical actual) {
        assertEquals("Name", expected.getName(), actual.getName());
        assertEquals("Category", expected.getCategory(), actual.getCategory());
        assertEquals("OneMonthCost", expected.getOneMonthCost(), actual.getOneMonthCost());
        assertEquals("Status", expected.getStatus(), actual.getStatus());
    }

    @Test
    public void findOneByName_Should_ReturnCorrectValues() throws Exception {
        Periodical expected = new Periodical();
        expected.setName(PERIODICAL_NAME);
        expected.setCategory(PeriodicalCategory.NEWS);
        expected.setOneMonthCost(22);
        expected.setStatus(Periodical.Status.INACTIVE);

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

        Periodical newPeriodical = new Periodical();
        newPeriodical.setName(NEW_PERIODICAL_NAME);
        newPeriodical.setCategory(PeriodicalCategory.NEWS);
        newPeriodical.setPublisher(NEW_PUBLISHER_NAME);
        newPeriodical.setOneMonthCost(ONE_MONTH_COST);
        newPeriodical.setStatus(Periodical.Status.ACTIVE);

        periodicalDao.createNew(newPeriodical);

        int numberAfter = periodicalDao.findAll().size();

        assertEquals(numberBefore, numberAfter - 1);

        Periodical periodicalFromDb = periodicalDao.findOneByName(NEW_PERIODICAL_NAME);

        assertPeriodicalData(newPeriodical, periodicalFromDb);
    }

    @Ignore(value = "change the status of the db")
    @Test
    public void update() throws Exception {
        Periodical periodicalToUpdate = new Periodical();
        periodicalToUpdate.setId(32);
        periodicalToUpdate.setName(NEW_PERIODICAL_NAME);
        periodicalToUpdate.setCategory(PeriodicalCategory.BUSINESS);
        periodicalToUpdate.setPublisher(NEW_PUBLISHER_NAME);
        periodicalToUpdate.setOneMonthCost(ONE_MONTH_COST + 10);
        periodicalToUpdate.setStatus(Periodical.Status.INACTIVE);

        periodicalDao.update(periodicalToUpdate);

        assertPeriodicalData(periodicalToUpdate, periodicalDao.findOneByName(NEW_PERIODICAL_NAME));

    }
}