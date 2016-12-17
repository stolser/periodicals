package com.stolser.javatraining.webproject.service.impl;

import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.credential.CredentialDao;
import com.stolser.javatraining.webproject.model.dao.role.RoleDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.storage.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private DaoFactory factory = DaoFactory.getMysqlDaoFactory();

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
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {

            User user = factory.getUserDao(conn).findOneById(id);
            if (user != null) {
                user.setRoles(factory.getRoleDao(conn).findRolesByUserName(user.getUserName()));
            }

            return user;

        } catch (SQLException e) {
            LOGGER.error("Exception during closing a connection.");
            throw new StorageException(e);
        }
    }

    @Override
    public Credential findOneCredentialByUserName(String userName) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            CredentialDao credentialDao = factory.getCredentialDao(conn);

            Credential credential = credentialDao.findCredentialByUserName(userName);

            return credential;

        } catch (SQLException e) {
            LOGGER.error("Exception during closing a connection.");
            throw new StorageException(e);
        }
    }

    @Override
    public User findOneUserByUserName(String userName) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("UserServiceImpl: connection has been got.");

            UserDao userDao = factory.getUserDao(conn);
            RoleDao roleDao = factory.getRoleDao(conn);
            User user = userDao.findUserByUserName(userName);

            if (user != null) {
                user.setRoles(roleDao.findRolesByUserName(userName));
            }

            System.out.println("user form the db: " + user);

            return user;
        } catch (SQLException e) {
            LOGGER.error("Exception during closing a connection.");
            throw new StorageException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            UserDao userDao = factory.getUserDao(conn);
            RoleDao roleDao = factory.getRoleDao(conn);

            List<User> allUser = userDao.findAll();

            allUser.forEach(user -> user.setRoles(roleDao.findRolesByUserName(user.getUserName())));

            return allUser;

        } catch (SQLException e) {
            LOGGER.error("Exception during closing a connection.");
            throw new StorageException(e);
        }
    }


}
