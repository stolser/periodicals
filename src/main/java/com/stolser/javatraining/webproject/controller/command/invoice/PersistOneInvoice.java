package com.stolser.javatraining.webproject.controller.command.invoice;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PersistOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOneInvoice.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        FrontendMessage message;
        String redirectUri = ApplicationResources.PERIODICAL_LIST_HREF;

        long periodicalId = Long.valueOf(request.getParameter("periodicalId"));
        long userIdFromUri = Utils.getFirstIdFromUri(request.getRequestURI());
        long userIdFromSession = Utils.getUserIdFromSession(request);

        if (userIdFromUri != userIdFromSession) {
            message = new FrontendMessage("validation.invoiceOperation.incorrectUserId",
                    FrontendMessage.MessageType.ERROR);

        } else {
            User user = new User();
            user.setId(userIdFromUri);
            Periodical periodical = PeriodicalService.getInstance().findOneById(periodicalId);

            if (periodical == null) {
                message = new FrontendMessage("validation.periodicalIsNull",
                        FrontendMessage.MessageType.ERROR);
            } else if (!Periodical.Status.VISIBLE.equals(periodical.getStatus())) {
                message = new FrontendMessage("validation.periodicalIsNotVisible",
                        FrontendMessage.MessageType.ERROR);
            } else {
                int subscriptionPeriod = Integer.valueOf(request.getParameter("subscriptionPeriod"));

                double totalSum = subscriptionPeriod * periodical.getOneMonthCost();

                System.out.println("Persisting one invoice:");
                System.out.println("user = " + user);
                System.out.println("periodical = " + periodical);
                System.out.println("subscriptionPeriod = " + subscriptionPeriod);

                Invoice newInvoice = new Invoice();
                newInvoice.setUser(user);
                newInvoice.setPeriodical(periodical);
                newInvoice.setSubscriptionPeriod(subscriptionPeriod);
                newInvoice.setTotalSum(totalSum);
                newInvoice.setCreationDate(Instant.now());
                newInvoice.setStatus(Invoice.Status.NEW);

                InvoiceServiceImpl.getInstance().createNew(newInvoice);
                message = new FrontendMessage("validation.invoiceCreated.success",
                        FrontendMessage.MessageType.SUCCESS);
                redirectUri = String.format("%s/%d",
                        ApplicationResources.PERIODICAL_LIST_HREF, periodicalId);

            }

        }

        try {
            messages.put("topMessages", message);
            request.getSession().setAttribute(ApplicationResources.MESSAGES_ATTR_NAME, messages);
            response.sendRedirect(redirectUri);

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
