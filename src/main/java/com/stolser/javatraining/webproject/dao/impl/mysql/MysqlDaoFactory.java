package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.*;
import com.stolser.javatraining.webproject.dao.exception.DaoException;

import java.sql.Connection;

import static java.util.Objects.isNull;

public class MysqlDaoFactory extends DaoFactory {
    private static final String SQL_CONNECTION_CAN_NOT_BE_NULL =
            "SQL connection can not be null. Datasource returned no connection.";
    private static final String CONNECTION_CAN_NOT_BE_NULL = "Connection can not be null.";
    private static final String CONNECTION_IS_NOT_AN_ABSTRACT_CONNECTION_IMPL_FOR_JDBC =
            "Connection is not an AbstractConnectionImpl for JDBC.";

    private MysqlDaoFactory() {}

    private static class InstanceHolder {
        private static final MysqlDaoFactory INSTANCE = new MysqlDaoFactory();
    }

    public static DaoFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public PeriodicalDao getPeriodicalDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlPeriodicalDao(getSqlConnection(conn));
    }

    @Override
    public CredentialDao getCredentialDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlCredentialDao(getSqlConnection(conn));
    }

    @Override
    public UserDao getUserDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlUserDao(getSqlConnection(conn));
    }

    @Override
    public RoleDao getRoleDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlRoleDao(getSqlConnection(conn));
    }

    @Override
    public SubscriptionDao getSubscriptionDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlSubscriptionDao(getSqlConnection(conn));
    }

    @Override
    public InvoiceDao getInvoiceDao(AbstractConnection conn) {
        checkConnection(conn);
        return new MysqlInvoiceDao(getSqlConnection(conn));
    }

    private void checkConnection(AbstractConnection conn) {
        if (isNull(conn)) {
            throw new DaoException(CONNECTION_CAN_NOT_BE_NULL);
        }

        if (!(conn instanceof AbstractConnectionImpl)) {
            throw new DaoException(CONNECTION_IS_NOT_AN_ABSTRACT_CONNECTION_IMPL_FOR_JDBC);
        }
    }

    private Connection getSqlConnection(AbstractConnection conn) {
        return ((AbstractConnectionImpl) conn).getConnection();
    }
}
