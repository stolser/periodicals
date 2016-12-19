package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.role.RoleDao;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.storage.ConnectionPool;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();
    private ConnectionPool connectionPool = ConnectionPoolProvider.getPool();

    private UserServiceImpl() {
    }

    private static class InstanceHolder {
        private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    /**
     * @return a singleton object of this type
     */
    public static UserService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public User findOneById(long id) {
        try (Connection conn = connectionPool.getConnection()) {

            User user = factory.getUserDao(conn).findOneById(id);

            if (user != null) {
                user.setRoles(factory.getRoleDao(conn).findRolesByUserName(user.getUserName()));
            }

            return user;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Credential findOneCredentialByUserName(String userName) {
        try (Connection conn = connectionPool.getConnection()) {

            return factory.getCredentialDao(conn).findCredentialByUserName(userName);

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public User findOneUserByUserName(String userName) {
        try (Connection conn = connectionPool.getConnection()) {
            User user = factory.getUserDao(conn).findOneByUserName(userName);

            if (user != null) {
                user.setRoles(factory.getRoleDao(conn).findRolesByUserName(userName));
            }

            return user;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection conn = connectionPool.getConnection()) {
            RoleDao roleDao = factory.getRoleDao(conn);

            List<User> allUser = factory.getUserDao(conn).findAll();

            allUser.forEach(user -> user.setRoles(roleDao.findRolesByUserName(user.getUserName())));

            return allUser;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


}
