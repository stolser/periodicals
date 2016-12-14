package com.stolser.javatraining.webproject.controller.request_processor.invoice;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceService;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ACCOUNT_HREF;
import static com.stolser.javatraining.webproject.controller.validator.Validator.STATUS_CODE_SUCCESS;

public class PayOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayOneInvoice.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        long invoiceId = HttpUtils.getFirstIdFromUri(request.getRequestURI()
                .replaceFirst("/backend/users/\\d+/", ""));
        Invoice invoiceInDb = InvoiceServiceImpl.getInstance().findOneById(invoiceId);

        if (validationPassed(invoiceInDb, request, generalMessages)) {
            generalMessages.add(new FrontendMessage(ApplicationResources.MSG_VALIDATION_PASSED_SUCCESS,
                    FrontendMessage.MessageType.INFO));
            tryToPayThisInvoice(invoiceId, request, generalMessages);
        }

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);
        HttpUtils.sendRedirect(request, response, CURRENT_USER_ACCOUNT_HREF);

        return null;
    }

    private boolean validationPassed(Invoice invoiceInDb, HttpServletRequest request,
                                     List<FrontendMessage> generalMessages) {
        ValidationResult result = new RequestUserIdValidator().validate(null, request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            generalMessages.add(new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));

            return false;
        }

        return invoiceExistsInDb(invoiceInDb, generalMessages)
                && invoiceIsNew(invoiceInDb, generalMessages)
                && periodicalIsVisible(invoiceInDb, generalMessages);
    }

    private boolean invoiceExistsInDb(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        if (invoiceInDb != null) {
            return true;
        } else {
            generalMessages.add(new FrontendMessage("validation.invoice.noSuchInvoice",
                    FrontendMessage.MessageType.ERROR));

            return false;
        }
    }

    private boolean invoiceIsNew(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        if (Invoice.Status.NEW.equals(invoiceInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(new FrontendMessage("validation.invoice.invoiceIsNotNew",
                    FrontendMessage.MessageType.ERROR));

            return false;
        }
    }

    private boolean periodicalIsVisible(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        long periodicalId = invoiceInDb.getPeriodical().getId();
        Periodical periodicalInDb = PeriodicalService.getInstance().findOneById(periodicalId);

        if (Periodical.Status.VISIBLE.equals(periodicalInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(new FrontendMessage("validation.periodicalIsNotVisible",
                    FrontendMessage.MessageType.ERROR));

            return false;
        }
    }

    private void tryToPayThisInvoice(long invoiceId, HttpServletRequest request,
                                     List<FrontendMessage> generalMessages) {
        InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

        try {
            if (invoiceService.payInvoice(invoiceId)) {
                generalMessages.add(new FrontendMessage("validation.invoiceWasPaid.success",
                        FrontendMessage.MessageType.SUCCESS));
            } else {
                generalMessages.add(new FrontendMessage("validation.invoice.payInvoiceError",
                        FrontendMessage.MessageType.ERROR));
            }

        } catch (Exception e) {
            LOGGER.error("User id = {}. Exception during paying the invoice with id {}.",
                    HttpUtils.getUserIdFromSession(request), invoiceId, e);

            generalMessages.add(new FrontendMessage("validation.invoice.payInvoiceError",
                    FrontendMessage.MessageType.ERROR));
        }
    }
}
