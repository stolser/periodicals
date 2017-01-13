package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.CredentialDao;
import com.stolser.javatraining.webproject.dao.exception.DaoException;
import com.stolser.javatraining.webproject.model.entity.user.Credential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MysqlCredentialDao implements CredentialDao {
    private static final String DB_CREDENTIALS_ID = "credentials.id";
    static final String DB_CREDENTIALS_USER_NAME = "credentials.user_name";
    private static final String DB_CREDENTIALS_PASSWORD_HASH = "credentials.password_hash";
    private static final String EXCEPTION_DURING_EXECUTION_STATEMENT =
            "Exception during execution statement '%s' for userName = %s.";
    private static final String EXCEPTION_DURING_CREATING_CREDENTIAL = "Exception during creating a credential.";
    private Connection conn;

    public MysqlCredentialDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Credential findCredentialByUserName(String userName) {
        String sqlStatement = "SELECT * FROM credentials " +
                "WHERE user_name = ?";

        try(PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            Credential credential = null;
            if (rs.next()) {
                Credential.Builder credentialBuilder = new Credential.Builder();
                credentialBuilder.setId(rs.getLong(DB_CREDENTIALS_ID))
                        .setUserName(rs.getString(DB_CREDENTIALS_USER_NAME))
                        .setPasswordHash(rs.getString(DB_CREDENTIALS_PASSWORD_HASH));
                credential = credentialBuilder.build();
            }

            return credential;

        } catch (SQLException e) {
            String message = String.format(EXCEPTION_DURING_EXECUTION_STATEMENT,
                    sqlStatement, userName);
            throw new DaoException(message, e);
        }
    }

    @Override
    public void createNew(Credential credential) {

        String sqlStatement = "INSERT INTO credentials " +
                "(user_name, password_hash, user_id) " +
                "VALUES (?, ?, ?)";

        try(PreparedStatement st = conn.prepareStatement(sqlStatement)) {
            st.setString(1, credential.getUserName());
            st.setString(2, credential.getPasswordHash());
            st.setLong(3, credential.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(EXCEPTION_DURING_CREATING_CREDENTIAL, e);
        }
    }
}
