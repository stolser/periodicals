package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UpdatePeriodical implements RequestProcessor {
    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());

        Periodical periodical = PeriodicalService.getInstance().findOneById(periodicalId);
        if (periodical == null) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        }

        System.out.println("found periodical: " + periodical);

        request.setAttribute(PERIODICAL_ATTR_NAME, periodical);
        request.setAttribute(ENTITY_OPERATION_TYPE_PARAM_NAME, "update");
        request.setAttribute("periodicalStatuses", Periodical.Status.values());

        return CREATE_EDIT_PERIODICAL_VIEW_NAME;
    }
}
