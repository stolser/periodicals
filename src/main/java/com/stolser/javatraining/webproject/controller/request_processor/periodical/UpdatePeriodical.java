package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UpdatePeriodical implements RequestProcessor {
    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());

        Periodical periodical = periodicalService.findOneById(periodicalId);

        if (periodical == null) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        }

        request.setAttribute(PERIODICAL_ATTR_NAME, periodical);
        request.setAttribute(ENTITY_OPERATION_TYPE_PARAM_ATTR_NAME, "update");
        request.setAttribute(PERIODICAL_STATUSES_ATTR_NAME, Periodical.Status.values());
        request.setAttribute(PERIODICAL_CATEGORIES_ATTR_NAME, PeriodicalCategory.values());

        return CREATE_EDIT_PERIODICAL_VIEW_NAME;
    }
}
