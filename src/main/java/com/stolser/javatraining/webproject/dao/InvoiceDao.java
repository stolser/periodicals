package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.time.Instant;
import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice> {
    List<Invoice> findAllByUserId(long userId);

    List<Invoice> findAllByPeriodicalId(long periodicalId);

    /**
     * Returns the sum of all invoices that were created during the specified time period
     * regardless whether they have been paid or not.
     * @param since the beginning of the time period
     * @param until the end of the time period
     */
    long getCreatedInvoiceSumByCreationDate(Instant since, Instant until);

    /**
     * Returns the sum of all invoices that were paid during the specified time period
     * regardless when they were created.
     * @param since the beginning of the time period
     * @param until the end of the time period
     */
    long getPaidInvoiceSumByPaymentDate(Instant since, Instant until);
}
