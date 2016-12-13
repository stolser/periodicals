package com.stolser.javatraining.webproject;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

public class Tester {
    public static void main(String[] args) {
        Invoice.Status status = Invoice.Status.valueOf("PAID");
        System.out.println("status = " + status);
//        try (Connection conn = ConnectionPoolProvider.getPool().getConnection()) {
//            System.out.println("connection has been got.");
//            PeriodicalDao periodicalDao = DaoFactory.getMysqlDaoFactory().getPeriodicalDao(conn);
//
//            System.out.println(periodicalDao.findOneById(2L));
//        } catch (SQLException e) {
//            System.out.println("Exception during closing a connection.");
//            throw new CustomSqlException(e);
//        }
    }
}
