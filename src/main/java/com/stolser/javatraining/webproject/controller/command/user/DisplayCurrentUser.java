package com.stolser.javatraining.webproject.controller.command.user;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayCurrentUser implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        long currentUserId = Utils.getUserIdFromSession(request);
        List<Invoice> invoices = InvoiceServiceImpl.getInstance().findAllByUserId(currentUserId);

        if (invoices.size() > 0) {
            invoices.forEach(invoice -> {
                long periodicalId = invoice.getPeriodical().getId();
                invoice.setPeriodical(PeriodicalService.getInstance().findOneById(periodicalId));
            });

            request.setAttribute("userInvoices", invoices);
        }

        return ApplicationResources.ONE_USER_INFO_VIEW_NAME;
    }
}
