package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.RequestResponseUtils;
import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
//        String idString = request.getRequestURI().replace("/backend/periodicals/update/", "");
        long periodicalId = RequestResponseUtils.getFirstIdFromUri(request.getRequestURI());

        Periodical periodical;
        try {
            periodical = PeriodicalService.getInstance().findOneById(periodicalId);
            request.setAttribute("periodical", periodical);

        } catch (CustomSqlException e) {
            String message = RequestResponseUtils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.error(message, e);

            throw new RuntimeException(message, e);
        }
        System.out.println("found periodical: " + periodical);

        request.setAttribute("entityOperationType", "update");
        request.setAttribute("statuses", Periodical.Status.values());

        return ApplicationResources.CREATE_EDIT_PERIODICAL_VIEW_NAME;
    }
}
