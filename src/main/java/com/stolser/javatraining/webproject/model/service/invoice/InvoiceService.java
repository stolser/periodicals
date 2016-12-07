package com.stolser.javatraining.webproject.model.service.invoice;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.util.List;

public interface InvoiceService {
    boolean createNew(Invoice newInvoice);
    boolean payInvoice(long invoiceId);
    List<Invoice> findAllByUserId(long id);
}
