package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontendMessage;
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

    private DeleteDiscardedPeriodicals() {}

    private static class InstanceHolder {
        private static final DeleteDiscardedPeriodicals INSTANCE = new DeleteDiscardedPeriodicals();
    }

    public static DeleteDiscardedPeriodicals getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = PERIODICAL_LIST_URI;
        List<FrontendMessage> generalMessages = new ArrayList<>();

        try {
            persistPeriodicalsToDeleteAndRelatedData();
            int deletedPeriodicalsNumber = periodicalService.deleteAllDiscarded();
            FrontendMessage message = (deletedPeriodicalsNumber > 0)
                    ? messageFactory.getSuccess(MSG_PERIODICALS_DELETED_SUCCESS)
                    : messageFactory.getWarning(MSG_NO_PERIODICALS_TO_DELETE);

            generalMessages.add(message);

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            response.sendRedirect(redirectUri);
            return null;

        } catch (IOException e) {
            throw new RuntimeException(HttpUtils.getRedirectionExceptionMessage(request,
                    redirectUri), e);
        }
    }

    private void persistPeriodicalsToDeleteAndRelatedData() {
        /*Here goes implementation of persisting somehow deleted data. It can be serialization into
        * files or saving into an archive database.*/
    }
}
