package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.CustomRedirectException;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteDiscardedPeriodicals implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = ApplicationResources.PERIODICAL_LIST_URI;
        List<FrontendMessage> generalMessages = new ArrayList<>();

        try {
            /*
            todo: Get all related invoices and subscriptions and serialize them;
            */

            List<Periodical> periodicalsToDelete = periodicalService
                    .findAllByStatus(Periodical.Status.DISCARDED);

            if (periodicalsToDelete.size() > 0) {
                periodicalService.deleteAllDiscarded();

                generalMessages.add(new FrontendMessage(ApplicationResources.MSG_PERIODICALS_DELETED_SUCCESS,
                        FrontendMessage.MessageType.SUCCESS));
            } else {
                generalMessages.add(new FrontendMessage(ApplicationResources.MSG_NO_PERIODICALS_TO_DELETE,
                        FrontendMessage.MessageType.WARNING));
            }

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            response.sendRedirect(redirectUri);
            return null;

        } catch (IOException e) {
            String message = HttpUtils.getRedirectionExceptionMessage(request,
                    redirectUri);

            throw new CustomRedirectException(message, e);
        }
    }
}
