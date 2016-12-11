package com.stolser.javatraining.webproject.controller.request_processor.invoice;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.RequestResponseUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.GENERAL_MESSAGES_FRONT_BLOCK_NAME;
import static com.stolser.javatraining.webproject.controller.validator.Validator.STATUS_CODE_SUCCESS;

public class PersistOneInvoice implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOneInvoice.class);
    private List<FrontendMessage> generalMessages = new ArrayList<>();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private BooleanSupplier periodicalExistsInDb;
    private BooleanSupplier periodicalIsVisible;
    private BooleanSupplier subscriptionPeriodIsValid;
    private Periodical periodicalInDb;
    private int subscriptionPeriod;
    private long periodicalId;

    @Override
    public String getViewName(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        request = httpRequest;
        response = httpResponse;
        periodicalId = Long.valueOf(request.getParameter("periodicalId"));
        periodicalInDb = PeriodicalService.getInstance().findOneById(periodicalId);

        specifyValidationRules();

        if (validationPassed()) {
            generalMessages.add(new FrontendMessage("validation.passedSuccessfully.success",
                    FrontendMessage.MessageType.INFO));
            tryToPersistNewInvoice(getNewInvoice());
        }

        addMessagesToSession();

        String redirectUri = String.format("%s/%d",
                ApplicationResources.PERIODICAL_LIST_HREF, periodicalId);

        RequestResponseUtils.sendRedirect(request, response, redirectUri);

        return null;
    }

    private void specifyValidationRules() {
        periodicalExistsInDb = () -> {
            if (periodicalInDb != null) {
                return true;
            } else {
                generalMessages.add(new FrontendMessage("validation.periodicalIsNull",
                        FrontendMessage.MessageType.ERROR));

                return false;
            }
        };

        periodicalIsVisible = () -> {
            if (Periodical.Status.VISIBLE.equals(periodicalInDb.getStatus())) {
                return true;
            } else {
                generalMessages.add(new FrontendMessage("validation.periodicalIsNotVisible",
                        FrontendMessage.MessageType.ERROR));

                return false;
            }
        };

        subscriptionPeriodIsValid = () -> {
            FrontendMessage message = new FrontendMessage("validation.subscriptionPeriodIsNotValid",
                    FrontendMessage.MessageType.ERROR);

            try {
                subscriptionPeriod = Integer.valueOf(request.getParameter("subscriptionPeriod"));

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
        };
    }

    private boolean validationPassed() {
        ValidationResult result = new RequestUserIdValidator().validate(null, request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            generalMessages.add(new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));

            return false;
        }

        return periodicalExistsInDb.getAsBoolean()
                && periodicalIsVisible.getAsBoolean()
                && subscriptionPeriodIsValid.getAsBoolean();
    }

    private void tryToPersistNewInvoice(Invoice invoiceToPersist) {
        try {
            InvoiceServiceImpl.getInstance().createNew(invoiceToPersist);

            generalMessages.add(new FrontendMessage("validation.invoiceCreated.success",
                    FrontendMessage.MessageType.SUCCESS));
        } catch (Exception e) {
            generalMessages.add(new FrontendMessage("validation.invoicePersistingFailed",
                    FrontendMessage.MessageType.ERROR));
        }
    }

    private Invoice getNewInvoice() {
        double totalSum = subscriptionPeriod * periodicalInDb.getOneMonthCost();
        long userIdFromUri = RequestResponseUtils.getFirstIdFromUri(request.getRequestURI());
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

    private void addMessagesToSession() {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(GENERAL_MESSAGES_FRONT_BLOCK_NAME, generalMessages);
        RequestResponseUtils.addMessagesToSession(request, frontMessageMap);
    }
}
