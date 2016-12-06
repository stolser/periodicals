package com.stolser.javatraining.webproject.model.service.invoice;

import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.util.List;

public interface InvoiceService {
    void createNew(Invoice newInvoice);
    void makeInvoicePaid(Invoice invoice);
    List<Invoice> findAllByUserId(long id);
}
