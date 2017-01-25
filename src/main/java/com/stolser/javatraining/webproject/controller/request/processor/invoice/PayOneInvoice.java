package com.stolser.javatraining.webproject.controller.request.processor.invoice;

import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form.validator.ValidatorFactory;
import com.stolser.javatraining.webproject.controller.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.InvoiceService;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a POST request to pay one invoice. In one transaction the status of the invoice is changed
 * to {@code paid} and a subscription is updated (created a new one or the status
 * and the end date are updated).
 */
public class PayOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayOneInvoice.class);
    private static final String EXCEPTION_DURING_PAYING_THE_INVOICE_WITH_ID =
            "User id = {}. Exception during paying invoice {}.";
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    private PayOneInvoice() {}

    private static class InstanceHolder {
        private static final PayOneInvoice INSTANCE = new PayOneInvoice();
    }

    public static PayOneInvoice getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        Invoice invoiceInDb = invoiceService.findOneById((long) getInvoiceIdFromRequest(request));

        if (isInvoiceValid(invoiceInDb, request, generalMessages)) {
            tryToPayInvoice(invoiceInDb, request, generalMessages);
        }

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);
        HttpUtils.sendRedirect(request, response, CURRENT_USER_ACCOUNT_URI);

        return Optional.empty();
    }

    private int getInvoiceIdFromRequest(HttpServletRequest request) {
        return HttpUtils.getFirstIdFromUri(request.getRequestURI()
                .replaceFirst("/backend/users/\\d+/", ""));
    }

    private boolean isInvoiceValid(Invoice invoiceInDb, HttpServletRequest request,
                                   List<FrontendMessage> generalMessages) {
        ValidationResult result = ValidatorFactory.getRequestUserIdValidator().validate(null, request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            generalMessages.add(messageFactory.getError(result.getMessageKey()));
            return false;
        }

        return invoiceExistsInDb(invoiceInDb, generalMessages)
                && isInvoiceNew(invoiceInDb, generalMessages)
                && isPeriodicalVisible(invoiceInDb, generalMessages);
    }

    private boolean invoiceExistsInDb(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        if (invoiceInDb != null) {
            return true;
        } else {
            generalMessages.add(messageFactory.getError(MSG_VALIDATION_NO_SUCH_INVOICE));
            return false;
        }
    }

    private boolean isInvoiceNew(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        if (Invoice.Status.NEW.equals(invoiceInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(messageFactory.getError(MSG_VALIDATION_INVOICE_IS_NOT_NEW));
            return false;
        }
    }

    private void tryToPayInvoice(Invoice invoiceInDb, HttpServletRequest request,
                                 List<FrontendMessage> generalMessages) {
        generalMessages.add(messageFactory.getInfo(MSG_VALIDATION_PASSED_SUCCESS));

        try {
            if (invoiceService.payInvoice(invoiceInDb)) {
                generalMessages.add(messageFactory.getSuccess(MSG_INVOICE_PAYMENT_SUCCESS));
            } else {
                generalMessages.add(messageFactory.getError(MSG_INVOICE_PAYMENT_ERROR));
            }

        } catch (RuntimeException e) {
            LOGGER.error(EXCEPTION_DURING_PAYING_THE_INVOICE_WITH_ID,
                    HttpUtils.getUserIdFromSession(request), invoiceInDb, e);

            generalMessages.add(messageFactory.getError(MSG_INVOICE_PAYMENT_ERROR));
        }
    }

    private boolean isPeriodicalVisible(Invoice invoiceInDb, List<FrontendMessage> generalMessages) {
        long periodicalId = invoiceInDb.getPeriodical().getId();
        Periodical periodicalInDb = periodicalService.findOneById(periodicalId);

        if (Periodical.Status.ACTIVE.equals(periodicalInDb.getStatus())) {
            return true;
        } else {
            generalMessages.add(messageFactory.getError(MSG_VALIDATION_PERIODICAL_IS_NOT_VISIBLE));
            return false;
        }
    }
}
