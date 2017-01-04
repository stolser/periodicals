package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.RoleDao;
import com.stolser.javatraining.webproject.dao.UserDao;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private static final String TEST_USERNAME = "testUsername";
    @Mock
    private DaoFactory factory;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection conn;
    @InjectMocks
    private UserService userService = UserServiceImpl.getInstance();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(connectionPool.getConnection()).thenReturn(conn);

        when(factory.getUserDao(conn)).thenReturn(userDao);
        when(factory.getRoleDao(conn)).thenReturn(roleDao);

    }

    @Test
    public void findOneUserByUserName_Should_SetRolesAndReturnCorrectUser() throws Exception {
        User user = mock(User.class);
        when(userDao.findOneByUserName(TEST_USERNAME)).thenReturn(user);

        assertEquals(user, userService.findOneUserByUserName(TEST_USERNAME));

        verify(user).setRoles(any());
        verify(roleDao).findRolesByUserName(TEST_USERNAME);
    }
}