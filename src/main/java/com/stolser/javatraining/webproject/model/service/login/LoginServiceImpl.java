package com.stolser.javatraining.webproject.model.service.login;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.dao.factory.DaoFactory;
import com.stolser.javatraining.webproject.model.database.ConnectionPool;
import com.stolser.javatraining.webproject.model.entity.user.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginServiceImpl implements LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    private static ConnectionPool staticPool;
    private static DaoFactory staticDaoFactory;

    private final DaoFactory daoFactory;
    private final ConnectionPool pool;

    private LoginServiceImpl(DaoFactory daoFactory, ConnectionPool pool) {
        this.daoFactory = daoFactory;
        this.pool = pool;
    }

    @Override
    public void save(Login login) {
        try (Connection conn = pool.getConnection()) {
            daoFactory.getLoginDao(conn).save(login);

        } catch (SQLException e) {
            String message = String.format("Exception during saving a login(%s) into the db. " +
                    "Caused by: %s", login, e.getMessage());
            LOGGER.debug(message);

            throw new CustomSqlException(message);
        }

    }

    private static class InstanceHolder {
        private static final LoginService INSTANCE = new LoginServiceImpl(staticDaoFactory, staticPool);
    }

    /**
     * This method must be called before getInstance() otherwise {@link IllegalStateException} will be thrown.
     * @param pool an instance of {@code ConnectionPool} that will be used by this service to get connections.
     */
    public static void setUp(DaoFactory loginDao, ConnectionPool pool) {
        checkNotNull(pool, "pool should not be null");
        staticDaoFactory = loginDao;
        staticPool = pool;
    }

    /**
     * @return a singleton object of this type
     */
    public static LoginService getInstance() {
        if (staticPool == null) {
            throw new IllegalStateException("You must call setUp() before using getInstance().");
        }
        return InstanceHolder.INSTANCE;
    }


}
