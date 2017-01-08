package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.AbstractConnection;
import com.stolser.javatraining.webproject.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractConnectionImpl implements AbstractConnection {
    private static final String CAN_NOT_BEGIN_TRANSACTION = "Can not begin transaction.";
    private static final String CAN_NOT_COMMIT_TRANSACTION = "Can not commit transaction";
    private static final String CAN_NOT_ROLLBACK_TRANSACTION = "Can not rollback transaction";
    private static final String CAN_NOT_CLOSE_CONNECTION = "Can not close connection";

    private Connection connection;
    private boolean transactionBegun = false;
    private boolean transactionCommitted = false;

    public AbstractConnectionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            transactionBegun = true;
        } catch (SQLException e) {
            throw new DaoException(CAN_NOT_BEGIN_TRANSACTION, e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            transactionCommitted = true;
        } catch (SQLException e) {
            throw new DaoException(CAN_NOT_COMMIT_TRANSACTION, e);
        }

    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            transactionCommitted = true;
        } catch (SQLException e) {
            throw new DaoException(CAN_NOT_ROLLBACK_TRANSACTION, e);
        }
    }

    @Override
    public void close() {
        try {
            if (transactionBegun && !transactionCommitted) {
                rollbackTransaction();
            }
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(CAN_NOT_CLOSE_CONNECTION, e);
        }
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
