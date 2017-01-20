package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;

import java.time.Instant;
import java.util.List;

public interface InvoiceService {
    Invoice findOneById(long invoiceId);

    List<Invoice> findAllByUserId(long userId);

    List<Invoice> findAllByPeriodicalId(long periodicalId);

    void createNew(Invoice newInvoice);

    /**
     * Updates the status of this invoice to {@code paid} and updates an existing subscription
     * (or creates a new one) as one transaction.
     *
     * @param invoiceToPay invoice id to be paid
     * @return true if after committing this invoice in the db has status 'PAID' and
     *      false otherwise.
     */
    boolean payInvoice(Invoice invoiceToPay);

    FinancialStatistics getFinStatistics(Instant since, Instant until);
}
