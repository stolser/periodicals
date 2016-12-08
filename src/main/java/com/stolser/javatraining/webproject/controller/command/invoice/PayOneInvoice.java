package com.stolser.javatraining.webproject.controller.command.invoice;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.invoice.Invoice;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceService;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class PayOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayOneInvoice.class);
    private List<FrontendMessage> generalMessages = new ArrayList<>();
    private BooleanSupplier userIsValid;
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

        System.out.println("paying invoice.");
        System.out.println("messages");

        specifyValidationRules();

        if (validationPassed()) {
            generalMessages.add(new FrontendMessage("validation.passedSuccessfully.success",
                    FrontendMessage.MessageType.INFO));
            tryToPayThisInvoice();
        }

        addMessagesToSession();

        return sendRedirect();
    }

    private void specifyValidationRules() {
        invoiceId = Utils.getFirstIdFromUri(request.getRequestURI()
                .replaceFirst("/adminPanel/users/\\d+/", ""));
        invoiceService = InvoiceServiceImpl.getInstance();
        final Invoice invoiceInDb = invoiceService.findOneById(invoiceId);

        userIsValid = () -> {
            long userIdFromUri = Utils.getFirstIdFromUri(request.getRequestURI());
            long userIdFromSession = Utils.getUserIdFromSession(request);

            if (userIdFromUri == userIdFromSession) {
                return true;
            } else {
                generalMessages.add(new FrontendMessage("validation.invoiceOperation.incorrectUserId",
                        FrontendMessage.MessageType.ERROR));

                return false;
            }
        };

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
        return userIsValid.getAsBoolean()
                && invoiceExistsInDb.getAsBoolean()
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
                    Utils.getUserIdFromSession(request), invoiceId, e);

            generalMessages.add(new FrontendMessage("validation.invoice.payInvoiceError",
                    FrontendMessage.MessageType.ERROR));
        }
    }

    private void addMessagesToSession() {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(GENERAL_MESSAGES_FRONT_BLOCK_NAME, generalMessages);
        Utils.addMessagesToSession(request, frontMessageMap);
    }

    private String sendRedirect() {
        String redirectUri = CURRENT_USER_ACCOUNT_HREF;
        try {
            response.sendRedirect(redirectUri);

            return null;

        } catch (Exception e) {
            String message = String.format("User id = %d. Exception during redirection to '%s'.",
                    Utils.getUserIdFromSession(request), redirectUri);
            LOGGER.error(message, e);

            throw new RuntimeException(message, e);
        }
    }
}
