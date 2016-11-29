package com.stolser.javatraining.webproject.model.service.user;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.dao.role.RoleDao;
import com.stolser.javatraining.webproject.model.dao.user.UserDao;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import com.stolser.javatraining.webproject.model.entity.user.Login;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final DaoFactory factory = DaoFactory.getMysqlDaoFactory();

    private UserService() {
    }

    private static class InstanceHolder {
        private static final UserService INSTANCE = new UserService();
    }

    /**
     * @return a singleton object of this type
     */
    public static UserService getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Login findOneLoginByUserName(String userName) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            UserDao userDao = factory.getUserDao(conn);

            Login login = userDao.findLoginByUserName(userName);

            return login;

        } catch (SQLException e) {
            LOGGER.debug("Exception during closing a connection.");
            throw new CustomSqlException(e);
        }
    }

    public User findOneUserByUserName(String userName) {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            System.out.println("UserService: connection has been got.");

            UserDao userDao = factory.getUserDao(conn);
            RoleDao roleDao = factory.getRoleDao(conn);
            User user = userDao.findUserByUserName(userName);

            user.setRoles(roleDao.findRolesByUserName(userName));

            System.out.println("user form the db: " + user);

            return user;
        } catch (SQLException e) {
            LOGGER.debug("Exception during closing a connection.");
            throw new CustomSqlException(e);
        }
    }

    public List<User> findAll() {
        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
            UserDao userDao = factory.getUserDao(conn);
            RoleDao roleDao = factory.getRoleDao(conn);

            List<User> allUser = userDao.findAll();

            allUser.forEach(user -> user.setRoles(roleDao.findRolesByUserName(user.getUserName())));

            return allUser;

        } catch (SQLException e) {
            LOGGER.debug("Exception during closing a connection.");
            throw new CustomSqlException(e);
        }
    }
}
