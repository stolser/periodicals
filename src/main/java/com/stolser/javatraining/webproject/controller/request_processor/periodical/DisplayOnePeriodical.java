package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

public class DisplayOnePeriodical implements RequestProcessor {

    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        Periodical periodical = PeriodicalService.getInstance().findOneById(periodicalId);

        if ((periodical == null) || (!Periodical.Status.VISIBLE.equals(periodical.getStatus()))) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        }

        System.out.println("found periodical: " + periodical);

        request.setAttribute("periodical", periodical);

        return ApplicationResources.ONE_PERIODICAL_VIEW_NAME;
    }
}
