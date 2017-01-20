package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.dao.impl.mysql.MysqlDaoFactory;

public abstract class DaoFactory {
    public abstract PeriodicalDao getPeriodicalDao(AbstractConnection conn);

    public abstract CredentialDao getCredentialDao(AbstractConnection conn);

    public abstract UserDao getUserDao(AbstractConnection conn);

    public abstract RoleDao getRoleDao(AbstractConnection conn);

    public abstract SubscriptionDao getSubscriptionDao(AbstractConnection conn);

    public abstract InvoiceDao getInvoiceDao(AbstractConnection conn);

    public static DaoFactory getMysqlDaoFactory() {
        return MysqlDaoFactory.getInstance();
    }
}
