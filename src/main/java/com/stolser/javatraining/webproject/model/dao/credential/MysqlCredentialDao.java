package com.stolser.javatraining.webproject.model.dao.credential;

import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlCredentialDao implements CredentialDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlCredentialDao.class);
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
                credential.setId(rs.getLong("id"));
                credential.setUserName(rs.getString("user_name"));
                credential.setPasswordHash(rs.getString("password_hash"));
            }

            return credential;

        } catch (SQLException e) {
            LOGGER.error("Exception during retrieving a credential with userName = {}", userName, e);
            throw new CustomSqlException(e);
        }
    }

}
