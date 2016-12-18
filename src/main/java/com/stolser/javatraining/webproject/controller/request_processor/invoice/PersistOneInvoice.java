package com.stolser.javatraining.webproject.controller.request_processor.invoice;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.front_message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.validator.front_message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.InvoiceService;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a POST request to create a new invoice.
 */
public class PersistOneInvoice implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        long periodicalId = Long.valueOf(request.getParameter(PERIODICAL_ID_PARAM_NAME));
        Periodical periodicalInDb = periodicalService.findOneById(periodicalId);

        if (validationPassed(periodicalInDb, request, generalMessages)) {
            generalMessages.add(messageFactory.getInfo(MSG_VALIDATION_PASSED_SUCCESS));
            tryToPersistNewInvoice(getNewInvoice(periodicalInDb, request), generalMessages);
        }

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);

        String redirectUri = String.format("%s/%d", PERIODICAL_LIST_URI, periodicalId);

        HttpUtils.sendRedirect(request, response, redirectUri);

        return null;
    }

    private boolean periodicalExistsInDb(Periodical periodicalInDb, List<FrontendMessage> generalMessages) {
        if (periodicalInDb != null) {
            return true;
        } else {
            generalMessages.add(messageFactory.getError(MSG_VALIDATION_PERIODICAL_IS_NULL));

            return false;
        }
    }

    private boolean periodicalIsVisible(Periodical periodicalInDb, List<FrontendMessage> generalMessages) {
        if (Periodical.Status.ACTIVE.equals(periodicalInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(messageFactory.getError(MSG_VALIDATION_PERIODICAL_IS_NOT_VISIBLE));

            return false;
        }
    }

    private boolean subscriptionPeriodIsValid(HttpServletRequest request,
                                              List<FrontendMessage> generalMessages) {
        FrontendMessage message = messageFactory.getError(MSG_VALIDATION_SUBSCRIPTION_PERIOD_IS_NOT_VALID);

        try {
            int subscriptionPeriod = Integer.valueOf(request.getParameter(SUBSCRIPTION_PERIOD_PARAM_NAME));

            if ((subscriptionPeriod >= 1) && (subscriptionPeriod <= 12)) {
                return true;
            } else {
                generalMessages.add(message);
                return false;
            }
        } catch (NumberFormatException e) {
            generalMessages.add(message);
            return false;
        }
    }

    private boolean validationPassed(Periodical periodicalInDb, HttpServletRequest request,
                                     List<FrontendMessage> generalMessages) {
        ValidationResult result = new RequestUserIdValidator().validate(null, request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            generalMessages.add(messageFactory.getError(result.getMessageKey()));

            return false;
        }

        return periodicalExistsInDb(periodicalInDb, generalMessages)
                && periodicalIsVisible(periodicalInDb, generalMessages)
                && subscriptionPeriodIsValid(request, generalMessages);
    }

    private void tryToPersistNewInvoice(Invoice invoiceToPersist, List<FrontendMessage> generalMessages) {
        try {
            invoiceService.createNew(invoiceToPersist);

            generalMessages.add(messageFactory.getSuccess(MSG_INVOICE_CREATION_SUCCESS));
        } catch (Exception e) {
            generalMessages.add(messageFactory.getError(MSG_INVOICE_PERSISTING_FAILED));
        }
    }

    private Invoice getNewInvoice(Periodical periodicalInDb, HttpServletRequest request) {
        int subscriptionPeriod = Integer.valueOf(request.getParameter(SUBSCRIPTION_PERIOD_PARAM_NAME));

        long totalSum = subscriptionPeriod * periodicalInDb.getOneMonthCost();
        long userIdFromUri = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        User user = new User();
        user.setId(userIdFromUri);

        Invoice newInvoice = new Invoice();
        newInvoice.setUser(user);
        newInvoice.setPeriodical(periodicalInDb);
        newInvoice.setSubscriptionPeriod(subscriptionPeriod);
        newInvoice.setTotalSum(totalSum);
        newInvoice.setCreationDate(Instant.now());
        newInvoice.setStatus(Invoice.Status.NEW);

        return newInvoice;
    }
}
