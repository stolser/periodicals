package com.stolser.javatraining.webproject.controller.request_processor.user;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Subscription;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalServiceImpl;
import com.stolser.javatraining.webproject.model.service.subscription.SubscriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class DisplayCurrentUser implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        long currentUserId = HttpUtils.getUserIdFromSession(request);
        List<Invoice> invoices = InvoiceServiceImpl.getInstance().findAllByUserId(currentUserId);
        List<Subscription> subscriptions = SubscriptionServiceImpl.getInstance().findAllByUserId(currentUserId);

        if (invoices.size() > 0) {
            invoices.forEach(invoice -> {
                long periodicalId = invoice.getPeriodical().getId();
                invoice.setPeriodical(PeriodicalServiceImpl.getInstance().findOneById(periodicalId));
            });

            sortInvoices(invoices);
            request.setAttribute(USER_INVOICES_PARAM_NAME, invoices);
        }

        if (subscriptions.size() > 0) {
            sortSubscriptions(subscriptions);
            request.setAttribute(USER_SUBSCRIPTIONS_PARAM_NAME, subscriptions);
        }

        return ONE_USER_INFO_VIEW_NAME;
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
                return first.getEndDate().compareTo(second.getEndDate());
            } else {
                return (first.getStatus() == Subscription.Status.ACTIVE) ? -1 : 1;
            }
        });
    }
}
