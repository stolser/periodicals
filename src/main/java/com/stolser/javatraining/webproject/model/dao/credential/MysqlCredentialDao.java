package com.stolser.javatraining.webproject.model.dao.credential;

import com.stolser.javatraining.webproject.model.storage.StorageException;
import com.stolser.javatraining.webproject.model.entity.user.Credential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class MysqlCredentialDao implements CredentialDao {
    private static final String EXCEPTION_DURING_EXECUTION_STATEMENT =
            "Exception during execution statement '%s' for userName = %s.";
    private Connection conn;

    public MysqlCredentialDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Credential findCredentialByUserName(String userName) {
        String sqlStatement = "SELECT * FROM credentials " +
                "WHERE user_name = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlStatement);
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            Credential credential = null;
            if (rs.next()) {
                credential = new Credential();
                credential.setId(rs.getLong(DB_CREDENTIALS_ID));
                credential.setUserName(rs.getString(DB_CREDENTIALS_USER_NAME));
                credential.setPasswordHash(rs.getString(DB_CREDENTIALS_PASSWORD_HASH));
            }

            return credential;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT,
                    sqlStatement, userName);
            throw new StorageException(message, e);
        }
    }

}
