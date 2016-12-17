package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;

import java.time.Instant;
import java.util.List;

public interface InvoiceService {
    Invoice findOneById(long invoiceId);
    List<Invoice> findAllByUserId(long userId);
    boolean createNew(Invoice newInvoice);
    boolean payInvoice(Invoice invoiceToPay);
    FinancialStatistics getFinStatistics(Instant since, Instant until);
}
