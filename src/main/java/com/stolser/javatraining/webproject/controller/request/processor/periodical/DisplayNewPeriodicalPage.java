package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.nonNull;

/**
 * Processes a GET request to a page where admin can create a new periodical.
 */
public class DisplayNewPeriodicalPage implements RequestProcessor {

    private DisplayNewPeriodicalPage() {}

    private static class InstanceHolder {
        private static final DisplayNewPeriodicalPage INSTANCE = new DisplayNewPeriodicalPage();
    }

    public static DisplayNewPeriodicalPage getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(PERIODICAL_ATTR_NAME);
        setRequestAttributes(request);

        return FORWARD + CREATE_EDIT_PERIODICAL_VIEW_NAME;
    }

    private void setRequestAttributes(HttpServletRequest request) {
        Periodical periodicalFromSession = getPeriodicalFromSession(request);
        Periodical periodicalToForward = nonNull(periodicalFromSession) ? periodicalFromSession : new Periodical();

        request.setAttribute(MESSAGES_ATTR_NAME, getMessagesFromSession(request));
        request.setAttribute(PERIODICAL_ATTR_NAME, periodicalToForward);
        request.setAttribute(PERIODICAL_STATUSES_ATTR_NAME, Periodical.Status.values());
        request.setAttribute(PERIODICAL_CATEGORIES_ATTR_NAME, PeriodicalCategory.values());
        request.setAttribute(PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME,
                Periodical.OperationType.CREATE.name().toLowerCase());
    }

    private Periodical getPeriodicalFromSession(HttpServletRequest request) {
        return (Periodical) request.getSession().getAttribute(PERIODICAL_ATTR_NAME);
    }

    @SuppressWarnings("unchecked")
    private Map<String, FrontendMessage> getMessagesFromSession(HttpServletRequest request) {
        return (Map<String, FrontendMessage>) request.getSession().getAttribute(MESSAGES_ATTR_NAME);
    }
}
