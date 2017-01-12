package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a GET request to a page where admin can create a new periodical.
 */
public class CreateNewPeriodical implements RequestProcessor {

    private CreateNewPeriodical() {}

    private static class InstanceHolder {
        private static final CreateNewPeriodical INSTANCE = new CreateNewPeriodical();
    }

    public static CreateNewPeriodical getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Periodical periodicalFromSession = (Periodical) session.getAttribute(PERIODICAL_ATTR_NAME);
        Periodical periodicalIntoRequest = (periodicalFromSession != null)
                ? periodicalFromSession
                : new Periodical();

        session.removeAttribute(PERIODICAL_ATTR_NAME);
        setRequestAttributes(request, session, periodicalIntoRequest);

        return CREATE_EDIT_PERIODICAL_VIEW_NAME;
    }

    private void setRequestAttributes(HttpServletRequest request, HttpSession session,
                                      Periodical periodicalIntoRequest) {
        @SuppressWarnings("unchecked")
        Map<String, FrontendMessage> messages = (Map<String, FrontendMessage>) session
                .getAttribute(MESSAGES_ATTR_NAME);

        request.setAttribute(MESSAGES_ATTR_NAME, messages);
        request.setAttribute(PERIODICAL_ATTR_NAME, periodicalIntoRequest);
        request.setAttribute(PERIODICAL_STATUSES_ATTR_NAME, Periodical.Status.values());
        request.setAttribute(PERIODICAL_CATEGORIES_ATTR_NAME, PeriodicalCategory.values());
        request.setAttribute(PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME,
                Periodical.OperationType.CREATE.name().toLowerCase());
    }
}
