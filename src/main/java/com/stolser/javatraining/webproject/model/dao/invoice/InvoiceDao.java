package com.stolser.javatraining.webproject.model.dao.invoice;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.time.Instant;
import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice> {
    List<Invoice> findAllByUserId(long userId);
    List<Invoice> findAllByPeriodicalId(long periodicalId);
    long getCreatedInvoiceSumByCreationDate(Instant since, Instant until);
    long getPaidInvoiceSumByPaymentDate(Instant since, Instant until);
}
