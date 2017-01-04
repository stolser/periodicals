package com.stolser.javatraining.webproject.controller.request.processor.user;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.subscription.Subscription;
import com.stolser.javatraining.webproject.service.InvoiceService;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.SubscriptionService;
import com.stolser.javatraining.webproject.service.impl.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;
import com.stolser.javatraining.webproject.service.impl.SubscriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a GET request to a current user personal account page.
 */
public class DisplayCurrentUser implements RequestProcessor {
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
    private SubscriptionService subscriptionService = SubscriptionServiceImpl.getInstance();
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        long currentUserId = HttpUtils.getUserIdFromSession(request);
        List<Invoice> invoices = invoiceService.findAllByUserId(currentUserId);
        List<Subscription> subscriptions = subscriptionService.findAllByUserId(currentUserId);

        if (thereAreInvoicesToDisplay(invoices)) {
            invoices.forEach(invoice -> {
                long periodicalId = invoice.getPeriodical().getId();
                invoice.setPeriodical(periodicalService.findOneById(periodicalId));
            });

            sortInvoices(invoices);
            request.setAttribute(USER_INVOICES_PARAM_NAME, invoices);
        }

        if (thereAreSubscriptionsToDisplay(subscriptions)) {
            sortSubscriptions(subscriptions);
            request.setAttribute(USER_SUBSCRIPTIONS_PARAM_NAME, subscriptions);
        }

        return ONE_USER_INFO_VIEW_NAME;
    }

    private boolean thereAreInvoicesToDisplay(List<Invoice> invoices) {
        return invoices.size() > 0;
    }

    private boolean thereAreSubscriptionsToDisplay(List<Subscription> subscriptions) {
        return subscriptions.size() > 0;
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
