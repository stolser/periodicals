package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.connection.pool.ConnectionPool;
import com.stolser.javatraining.webproject.connection.pool.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private UserServiceImpl() {
    }

    private static class InstanceHolder {
        private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public User findOneById(long id) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            User user = factory.getUserDao(conn).findOneById(id);
            setUserRoles(user, conn);

            return user;
        }
    }

    private void setUserRoles(User user, AbstractConnection conn) {
        if (user != null) {
            user.setRoles(factory.getRoleDao(conn).findRolesByUserName(user.getUserName()));
        }
    }

    @Override
    public Credential findOneCredentialByUserName(String userName) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            return factory.getCredentialDao(conn).findCredentialByUserName(userName);
        }
    }

    @Override
    public User findOneUserByUserName(String userName) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            User user = factory.getUserDao(conn).findOneByUserName(userName);
            setUserRoles(user, conn);

            return user;
        }
    }

    @Override
    public List<User> findAll() {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            List<User> allUser = factory.getUserDao(conn).findAll();
            allUser.forEach(user -> user.setRoles(factory.getRoleDao(conn)
                    .findRolesByUserName(user.getUserName())));

            return allUser;
        }
    }

    @Override
    public void createNewUser(User user, Credential credential, String userRole) {
        try (AbstractConnection conn = connectionPool.getConnection()) {
            conn.beginTransaction();

            long userId = factory.getUserDao(conn).createNew(user);
//            factory.getRoleDao(conn).addRole(userId, userRole);
//            factory.getCredentialDao(conn).createNew(credential);

            conn.commitTransaction();
        }
    }
}
