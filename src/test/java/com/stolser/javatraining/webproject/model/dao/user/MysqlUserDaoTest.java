package com.stolser.javatraining.webproject.model.dao.user;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MysqlUserDaoTest {
    private static final int ADMIN_ID = 1;
    private static UserDao userDao;
    private static Connection conn;
    private static DaoFactory factory;
    private static User expected;

    @BeforeClass
    public static void setUp() throws Exception {
        conn = ConnectionPoolProvider.getTestPool().getConnection();
        factory = DaoFactory.getMysqlDaoFactory();
        userDao = factory.getUserDao(conn);

        expected = new User();
        expected.setUserName("stolser");
        expected.setFirstName("Oleg");
        expected.setLastName("Stoliarov");
        expected.setEmail("stolser@gmail.com");
        expected.setStatus(User.Status.ACTIVE);

    }

    @Test
    public void findOneById_Should_ReturnCorrectUser() throws Exception {

        assertUserData(userDao.findOneById(ADMIN_ID));
    }

    private void assertUserData(User actual) {
        assertEquals("UserName", expected.getUserName(), actual.getUserName());
        assertEquals("FirstName", expected.getFirstName(), actual.getFirstName());
        assertEquals("LastName", expected.getLastName(), actual.getLastName());
        assertEquals("Email", expected.getEmail(), actual.getEmail());
        assertEquals("Status", expected.getStatus(), actual.getStatus());
    }

    @Test
    public void findUserByUserName_Should_ReturnCorrectUser() {
        assertUserData(userDao.findOneByUserName("stolser"));
    }

    @Test
    public void findUserByUserName_Should_ReturnNull() {
        assertNull(userDao.findOneByUserName("stolser2"));
    }

    @Test
    public void findAll_Should_ReturnAllUsersFromDb() {
        int expectedNumber = 4;
        int actualNumber = userDao.findAll().size();

        assertEquals(expectedNumber, actualNumber);
    }


    @AfterClass
    public static void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}