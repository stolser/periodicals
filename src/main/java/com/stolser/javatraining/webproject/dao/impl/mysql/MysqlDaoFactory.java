package com.stolser.javatraining.webproject.dao.impl.mysql;

import com.stolser.javatraining.webproject.dao.InvoiceDao;
import com.stolser.javatraining.webproject.dao.DaoFactory;
import com.stolser.javatraining.webproject.dao.CredentialDao;
import com.stolser.javatraining.webproject.dao.PeriodicalDao;
import com.stolser.javatraining.webproject.dao.RoleDao;
import com.stolser.javatraining.webproject.dao.SubscriptionDao;
import com.stolser.javatraining.webproject.dao.UserDao;

import java.sql.Connection;

public class MysqlDaoFactory extends DaoFactory {
    private MysqlDaoFactory() {}

    private static class InstanceHolder {
        private static final MysqlDaoFactory INSTANCE = new MysqlDaoFactory();
    }

    public static DaoFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public PeriodicalDao getPeriodicalDao(Connection conn) {
        return new MysqlPeriodicalDao(conn);
    }

    @Override
    public CredentialDao getCredentialDao(Connection conn) {
        return new MysqlCredentialDao(conn);
    }

    @Override
    public UserDao getUserDao(Connection conn) {
        return new MysqlUserDao(conn);
    }

    @Override
    public RoleDao getRoleDao(Connection conn) {
        return new MysqlRoleDao(conn);
    }

    @Override
    public SubscriptionDao getSubscriptionDao(Connection conn) {
        return new MysqlSubscriptionDao(conn);
    }

    @Override
    public InvoiceDao getInvoiceDao(Connection conn) {
        return new MysqlInvoiceDao(conn);
    }
}
