package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.dao.impl.mysql.MysqlDaoFactory;

import java.sql.Connection;

public abstract class DaoFactory {
    public abstract PeriodicalDao getPeriodicalDao(Connection conn);
    public abstract CredentialDao getCredentialDao(Connection conn);
    public abstract UserDao getUserDao(Connection conn);
    public abstract RoleDao getRoleDao(Connection conn);
    public abstract SubscriptionDao getSubscriptionDao(Connection conn);
    public abstract InvoiceDao getInvoiceDao(Connection conn);

    public static DaoFactory getMysqlDaoFactory() {
        return new MysqlDaoFactory();
    }
}
