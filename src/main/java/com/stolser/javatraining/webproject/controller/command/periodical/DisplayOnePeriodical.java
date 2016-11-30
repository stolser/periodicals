package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayOnePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        String idString = request.getRequestURI().replace("/adminPanel/periodicals/", "");
        long periodicalId = Integer.valueOf(idString);

        Periodical periodical;
        try {
            periodical = PeriodicalService.getInstance().findOne(periodicalId);

        } catch (CustomSqlException e) {
            String message = Utils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.debug(message, e);

            throw new RuntimeException(message);
        }
        System.out.println("found periodical: " + periodical);

        request.setAttribute("periodical", periodical);

        return ApplicationResources.ONE_PERIODICAL_VIEW_NAME;
    }
}
