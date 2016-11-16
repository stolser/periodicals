package com.stolser.javatraining.webproject.model.service.dbsetup;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.database.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.google.common.base.Preconditions.checkNotNull;

public class DbSetupServiceImpl implements DbSetupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbSetupServiceImpl.class);
    private static ConnectionPool staticPool;

    private final ConnectionPool pool;

    private DbSetupServiceImpl(ConnectionPool pool) {
        this.pool = pool;
    }

    private static class InstanceHolder {
        private static final DbSetupService INSTANCE = new DbSetupServiceImpl(staticPool);
    }

    /**
     * This method must be called before getInstance() otherwise {@link IllegalStateException} will be thrown.
     * @param pool an instance of {@code ConnectionPool} that will be used by this service to get connections.
     */
    public static void setUp(ConnectionPool pool) {
        checkNotNull(pool, "pool should not be null");
        staticPool = pool;
    }


    /**
     * @return a singleton object of this type
     */
    public static DbSetupService getInstance() {
        if (staticPool == null) {
            throw new IllegalStateException("You must call setUp() before using getInstance().");
        }
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void createSchema() {
        String createUsersTable = "CREATE TABLE users(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "gender ENUM('male', 'female')," +
                "birthday DATETIME," +
                "phone VARCHAR(30)," +
                "email VARCHAR(50) NOT NULL," +
                "address VARCHAR(100)," +
                "status ENUM('active', 'blocked') NOT NULL," +
                "CONSTRAINT UNIQUE (email));";

        String createLoginsTable = "CREATE TABLE logins(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "login VARCHAR(30) NOT NULL," +
                "passwordSalt VARCHAR(200)," +
                "passwordHash VARCHAR(200)," +
                "userId BIGINT," +
                "registrDate DATETIME NOT NULL," +
                "CONSTRAINT UNIQUE (login)," +
                "CONSTRAINT FOREIGN KEY (userId) REFERENCES users(id));";

        try (Connection conn = pool.getConnection();
                Statement st = conn.createStatement()) {

            st.executeUpdate(createUsersTable);
            LOGGER.debug("'users' table has been dropped and created again.");

            st.executeUpdate(createLoginsTable);
            LOGGER.debug("'logins' table has been dropped and created again.");

        } catch (SQLException e) {
            String message = "Exception during creating 'Users' table.";
            LOGGER.debug(message);

            throw new CustomSqlException(message);
        }
    }
}
