package com.stolser.javatraining.webproject.model.service.dbsetup;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.database.ConnectionPoolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSetupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbSetupService.class);

    private DbSetupService() {
    }

    private static class InstanceHolder {
        private static final DbSetupService INSTANCE = new DbSetupService();
    }

    /**
     * @return a singleton object of this type
     */
    public static DbSetupService getInstance() {
        return InstanceHolder.INSTANCE;
    }

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

        try (Connection conn = ConnectionPoolProvider.getPool().getConnection();
             Statement st = conn.createStatement()) {

            st.executeUpdate(createUsersTable);
            LOGGER.error("'users' table has been dropped and created again.");

            st.executeUpdate(createLoginsTable);
            LOGGER.error("'logins' table has been dropped and created again.");

        } catch (SQLException e) {
            String message = "Exception during creating 'Users' table.";
            LOGGER.error(message);

            throw new CustomSqlException(message);
        }
    }
}
