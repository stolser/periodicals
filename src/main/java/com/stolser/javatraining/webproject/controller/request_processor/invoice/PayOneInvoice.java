package com.stolser.javatraining.webproject.controller.request_processor.invoice;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceService;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ACCOUNT_HREF;
import static com.stolser.javatraining.webproject.controller.validator.Validator.STATUS_CODE_SUCCESS;

public class PayOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayOneInvoice.class);
    private List<FrontendMessage> generalMessages = new ArrayList<>();
    private BooleanSupplier invoiceExistsInDb;
    private BooleanSupplier invoiceIsNew;
    private long invoiceId;
    private InvoiceService invoiceService;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public String getViewName(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        request = httpRequest;
        response = httpResponse;

        specifyValidationRules();

        if (validationPassed()) {
            generalMessages.add(new FrontendMessage("validation.passedSuccessfully.success",
                    FrontendMessage.MessageType.INFO));
            tryToPayThisInvoice();
        }

        HttpUtils.addGeneralMessagesToSession(httpRequest, generalMessages);
        HttpUtils.sendRedirect(request, response, CURRENT_USER_ACCOUNT_HREF);

        return null;
    }

    private void specifyValidationRules() {
        invoiceId = HttpUtils.getFirstIdFromUri(request.getRequestURI()
                .replaceFirst("/backend/users/\\d+/", ""));
        invoiceService = InvoiceServiceImpl.getInstance();
        final Invoice invoiceInDb = invoiceService.findOneById(invoiceId);

        invoiceExistsInDb = () -> {
            if (invoiceInDb != null) {
                return true;
            } else {
                generalMessages.add(new FrontendMessage("validation.invoice.noSuchInvoice",
                        FrontendMessage.MessageType.ERROR));

                return false;
            }
        };

        invoiceIsNew = () -> {
            if (Invoice.Status.NEW.equals(invoiceInDb.getStatus())) {
                return true;
            } else {
                generalMessages.add(new FrontendMessage("validation.invoice.invoiceIsNotNew",
                        FrontendMessage.MessageType.ERROR));

                return false;
            }
        };
    }

    private boolean validationPassed() {
        ValidationResult result = new RequestUserIdValidator().validate(null, request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            generalMessages.add(new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));

            return false;
        }

        return invoiceExistsInDb.getAsBoolean()
                && invoiceIsNew.getAsBoolean();
    }

    private void tryToPayThisInvoice() {
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
