package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.PeriodicalDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PeriodicalServiceImplTest {
    private static final int PERIODICAL_ID = 10;
    private static final String TEST_NAME = "Test name";
    private static final int NEW_PERIODICAL_ID = 11;
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private PeriodicalDao periodicalDao;
    @Mock
    private Connection conn;

    @InjectMocks
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private Periodical periodical;
    private Periodical newPeriodical;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        periodical = new Periodical();
        periodical.setName(TEST_NAME);

        newPeriodical = new Periodical();
        newPeriodical.setId(NEW_PERIODICAL_ID);

        when(connectionPool.getConnection()).thenReturn(conn);
        when(factory.getPeriodicalDao(conn)).thenReturn(periodicalDao);
    }

    @Test
    public void save_Should_CreateNewPeriodical_IfIdIsZero() throws Exception {
        periodical.setId(0);
        when(periodicalDao.findOneByName(TEST_NAME)).thenReturn(newPeriodical);

        assertEquals(NEW_PERIODICAL_ID, periodicalService.save(periodical).getId());

        verify(periodicalDao, times(1)).createNew(periodical);
        verify(periodicalDao, times(1)).findOneByName(TEST_NAME);

    }

    @Test
    public void save_Should_UpdateExistingInDbPeriodical() {
        periodical.setId(PERIODICAL_ID);
        when(periodicalDao.findOneById(PERIODICAL_ID)).thenReturn(periodical);
        when(periodicalDao.findOneByName(TEST_NAME)).thenReturn(periodical);

        assertEquals(PERIODICAL_ID, periodicalService.save(periodical).getId());

        verify(periodicalDao).update(periodical);

    }

    @Test(expected = NoSuchElementException.class)
    public void save_Should_ThrowExceptionIfIdIsNotZero_AndPeriodicalDoesNotExist() {
        periodical.setId(PERIODICAL_ID);
        when(periodicalDao.findOneById(PERIODICAL_ID)).thenReturn(null);

        periodicalService.save(periodical).getId();
    }

    @Test
    public void updateAndSetDiscarded() throws Exception {
        periodicalService.updateAndSetDiscarded(periodical);

        verify(periodicalDao).updateAndSetDiscarded(periodical);
    }

    @Test
    public void deleteAllDiscarded() throws Exception {
        periodicalService.deleteAllDiscarded();

        verify(periodicalDao).deleteAllDiscarded();
    }
}