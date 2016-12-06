package com.stolser.javatraining.webproject.model.dao.invoice;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;

import java.util.List;

public interface InvoiceDao extends GenericDao<Invoice> {
    List<Invoice> findAllByUserId(long id);
}
