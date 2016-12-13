package com.stolser.javatraining.webproject.controller.request_processor.invoice;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static com.stolser.javatraining.webproject.controller.validator.Validator.STATUS_CODE_SUCCESS;

public class PersistOneInvoice implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        long periodicalId = Long.valueOf(request.getParameter(PERIODICAL_ID_PARAM_NAME));
        Periodical periodicalInDb = PeriodicalService.getInstance().findOneById(periodicalId);

        if (validationPassed(periodicalInDb, request, generalMessages)) {
            generalMessages.add(new FrontendMessage(MSG_VALIDATION_PASSED_SUCCESS,
                    FrontendMessage.MessageType.INFO));
            tryToPersistNewInvoice(getNewInvoice(periodicalInDb, request), generalMessages);
        }

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);

        String redirectUri = String.format("%s/%d",
                PERIODICAL_LIST_HREF, periodicalId);

        HttpUtils.sendRedirect(request, response, redirectUri);

        return null;
    }

    private boolean periodicalExistsInDb(Periodical periodicalInDb, List<FrontendMessage> generalMessages) {
        if (periodicalInDb != null) {
            return true;
        } else {
            generalMessages.add(new FrontendMessage("validation.periodicalIsNull",
                    FrontendMessage.MessageType.ERROR));

            return false;
        }
    }

    private boolean periodicalIsVisible(Periodical periodicalInDb, List<FrontendMessage> generalMessages) {
        if (Periodical.Status.VISIBLE.equals(periodicalInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(new FrontendMessage("validation.periodicalIsNotVisible",
                    FrontendMessage.MessageType.ERROR));

            return false;
        }
    }

    private boolean subscriptionPeriodIsValid(HttpServletRequest request,
                                              List<FrontendMessage> generalMessages) {
        FrontendMessage message = new FrontendMessage("validation.subscriptionPeriodIsNotValid",
                FrontendMessage.MessageType.ERROR);

        try {
            int subscriptionPeriod = Integer.valueOf(request.getParameter("subscriptionPeriod"));

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
            generalMessages.add(new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));

            return false;
        }

        return periodicalExistsInDb(periodicalInDb, generalMessages)
                && periodicalIsVisible(periodicalInDb, generalMessages)
                && subscriptionPeriodIsValid(request, generalMessages);
    }

    private void tryToPersistNewInvoice(Invoice invoiceToPersist, List<FrontendMessage> generalMessages) {
        try {
            InvoiceServiceImpl.getInstance().createNew(invoiceToPersist);

            generalMessages.add(new FrontendMessage("validation.invoiceCreated.success",
                    FrontendMessage.MessageType.SUCCESS));
        } catch (Exception e) {
            generalMessages.add(new FrontendMessage("validation.invoicePersistingFailed",
                    FrontendMessage.MessageType.ERROR));
        }
    }

    private Invoice getNewInvoice(Periodical periodicalInDb, HttpServletRequest request) {
        int subscriptionPeriod = Integer.valueOf(request.getParameter("subscriptionPeriod"));

        double totalSum = subscriptionPeriod * periodicalInDb.getOneMonthCost();
        long userIdFromUri = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        Invoice newInvoice = new Invoice();
        User user = new User();
        user.setId(userIdFromUri);
        newInvoice.setUser(user);
        newInvoice.setPeriodical(periodicalInDb);
        newInvoice.setSubscriptionPeriod(subscriptionPeriod);
        newInvoice.setTotalSum(totalSum);
        newInvoice.setCreationDate(Instant.now());
        newInvoice.setStatus(Invoice.Status.NEW);

        return newInvoice;
    }
}
