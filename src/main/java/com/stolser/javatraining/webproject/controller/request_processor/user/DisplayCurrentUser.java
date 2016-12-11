package com.stolser.javatraining.webproject.controller.request_processor.user;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import com.stolser.javatraining.webproject.model.service.subscription.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

public class DisplayCurrentUser implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        long currentUserId = HttpUtils.getUserIdFromSession(request);
        List<Invoice> invoices = InvoiceServiceImpl.getInstance().findAllByUserId(currentUserId);
        List<Subscription> subscriptions = SubscriptionService.getInstance().findAllByUserId(currentUserId);

        if (invoices.size() > 0) {
            invoices.forEach(invoice -> {
                long periodicalId = invoice.getPeriodical().getId();
                invoice.setPeriodical(PeriodicalService.getInstance().findOneById(periodicalId));
            });

            sortInvoices(invoices);
            request.setAttribute("userInvoices", invoices);
        }

        if (subscriptions.size() > 0) {
            sortSubscriptions(subscriptions);
            request.setAttribute("userSubscriptions", subscriptions);
        }

        return ApplicationResources.ONE_USER_INFO_VIEW_NAME;
    }

    private void sortInvoices(List<Invoice> invoices) {
        Collections.sort(invoices, (first, second) -> {
            if (first.getStatus() == second.getStatus()) {
                if (Invoice.Status.NEW.equals(first.getStatus())) {
                    return second.getCreationDate().compareTo(first.getCreationDate());
                } else {
                    return second.getPaymentDate().compareTo(first.getPaymentDate());
                }
            } else {
                return (first.getStatus() == Invoice.Status.NEW) ? -1 : 1;
            }
        });
    }

    private void sortSubscriptions(List<Subscription> subscriptions) {
        Collections.sort(subscriptions, (first, second) -> {
            if (first.getStatus() == second.getStatus()) {
                return second.getEndDate().compareTo(first.getEndDate());
            } else {
                return (first.getStatus() == Subscription.Status.ACTIVE) ? -1 : 1;
            }
        });
    }
}
