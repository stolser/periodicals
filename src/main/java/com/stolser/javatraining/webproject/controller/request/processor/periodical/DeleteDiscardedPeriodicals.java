package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a POST request to delete all periodicals with status = "discarded".
 */
public class DeleteDiscardedPeriodicals implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    private DeleteDiscardedPeriodicals() {
    }

    private static class InstanceHolder {
        private static final DeleteDiscardedPeriodicals INSTANCE = new DeleteDiscardedPeriodicals();
    }

    public static DeleteDiscardedPeriodicals getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();

        persistPeriodicalsToDeleteAndRelatedData();
        int deletedPeriodicalsNumber = periodicalService.deleteAllDiscarded();
        addDeleteResultMessage(generalMessages, deletedPeriodicalsNumber);

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);

        return REDIRECT + PERIODICAL_LIST_URI;
    }

    private void addDeleteResultMessage(List<FrontendMessage> generalMessages, int deletedPeriodicalsNumber) {
        FrontendMessage message = (deletedPeriodicalsNumber > 0)
                ? messageFactory.getSuccess(MSG_PERIODICALS_DELETED_SUCCESS)
                : messageFactory.getWarning(MSG_NO_PERIODICALS_TO_DELETE);

        generalMessages.add(message);
    }

    private void persistPeriodicalsToDeleteAndRelatedData() {
        /*Here goes implementation of persisting somehow deleted data. It can be serialization into
        * files or saving into an archive database.*/
    }
}
