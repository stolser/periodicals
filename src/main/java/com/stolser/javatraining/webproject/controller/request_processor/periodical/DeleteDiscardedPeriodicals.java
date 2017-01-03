package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.form_validator.front_message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.form_validator.front_message.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a POST request to delete all periodicals with status = "discarded".
 */
public class DeleteDiscardedPeriodicals implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = PERIODICAL_LIST_URI;
        List<FrontendMessage> generalMessages = new ArrayList<>();

        try {
            List<Periodical> periodicalsToDelete = periodicalService
                    .findAllByStatus(Periodical.Status.DISCARDED);

            if (periodicalsToDelete.size() > 0) {
                persistPeriodicalsAndRelatedInvoices(periodicalsToDelete);
                periodicalService.deleteAllDiscarded();

                generalMessages.add(messageFactory.getSuccess(MSG_PERIODICALS_DELETED_SUCCESS));
            } else {
                generalMessages.add(messageFactory.getWarning(MSG_NO_PERIODICALS_TO_DELETE));
            }

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            response.sendRedirect(redirectUri);
            return null;

        } catch (IOException e) {
            String message = HttpUtils.getRedirectionExceptionMessage(request,
                    redirectUri);

            throw new RuntimeException(message, e);
        }
    }

    private void persistPeriodicalsAndRelatedInvoices(List<Periodical> periodicalsToDelete) {
        /*Here goes implementation of persisting somehow deleted data. It can be serialization into
        * files or saving into archive database.*/
    }
}
