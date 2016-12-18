package com.stolser.javatraining.webproject.model.dao.invoice;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.time.Instant;
import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice> {
    List<Invoice> findAllByUserId(long userId);
    List<Invoice> findAllByPeriodicalId(long periodicalId);

    /**
     * @param since the beginning of the time period
     * @param until the end of the time period
     * @return the sum of all invoices that were created during the specified time period
     * regardless whether they have been paid or not
     */
    long getCreatedInvoiceSumByCreationDate(Instant since, Instant until);

    /**
     * @param since the beginning of the time period
     * @param until the end of the time period
     * @return the sum of all invoices that were paid during the specified time period
     * regardless when they were created
     */
    long getPaidInvoiceSumByPaymentDate(Instant since, Instant until);
}
