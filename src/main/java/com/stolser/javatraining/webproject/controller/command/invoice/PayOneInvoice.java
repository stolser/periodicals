package com.stolser.javatraining.webproject.controller.command.invoice;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
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
import java.util.HashMap;
import java.util.Map;

public class PayOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayOneInvoice.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        FrontendMessage message;
        String redirectUri = ApplicationResources.CURRENT_USER_ACCOUNT_HREF;

        long userIdFromUri = Utils.getFirstIdFromUri(request.getRequestURI());
        long userIdFromSession = Utils.getUserIdFromSession(request);

        long invoiceId = Utils.getFirstIdFromUri(request.getRequestURI()
                .replaceFirst("/adminPanel/users/\\d+/", ""));

        if (userIdFromUri != userIdFromSession) {
            message = new FrontendMessage("validation.invoiceOperation.incorrectUserId",
                    FrontendMessage.MessageType.ERROR);
        } else {
            InvoiceService invoiceService = InvoiceServiceImpl.getInstance();
            Invoice invoiceInDb = invoiceService.findOneById(invoiceId);

            if (invoiceInDb == null) {
                message = new FrontendMessage("validation.invoice.noSuchInvoice",
                        FrontendMessage.MessageType.ERROR);
            } else {

                if (!Invoice.Status.NEW.equals(invoiceInDb.getStatus())) {
                    message = new FrontendMessage("validation.invoice.invoiceIsNotNew",
                            FrontendMessage.MessageType.ERROR);
                } else {

                    try {
                        if (invoiceService.payInvoice(invoiceId)) {
                            message = new FrontendMessage("validation.invoiceWasPaid.success",
                                    FrontendMessage.MessageType.SUCCESS);
                        } else {
                            message = new FrontendMessage("validation.invoice.payInvoiceError",
                                    FrontendMessage.MessageType.ERROR);
                        }

                    } catch (Exception e) {
                        LOGGER.error("Exception during paying the invoice with id {}.", invoiceId, e);

                        message = new FrontendMessage("validation.invoice.payInvoiceError",
                                FrontendMessage.MessageType.ERROR);
                    }
                }

            }

        }

        messages.put("topMessages", message);
        request.getSession().setAttribute(ApplicationResources.MESSAGES_ATTR_NAME, messages);

        try {
            response.sendRedirect(redirectUri);

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
