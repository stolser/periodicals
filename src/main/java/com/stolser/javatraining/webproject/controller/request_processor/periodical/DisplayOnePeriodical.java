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
import java.util.NoSuchElementException;

public class DisplayOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayOnePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        long periodicalId = RequestResponseUtils.getFirstIdFromUri(request.getRequestURI());

        Periodical periodical;
        try {
            periodical = PeriodicalService.getInstance().findOneById(periodicalId);

            if ((periodical == null) || (!Periodical.Status.VISIBLE.equals(periodical.getStatus()))) {
                throw new NoSuchElementException(
                        String.format("There is no periodical with id %d in the db.", periodicalId));
            }

        } catch (CustomSqlException e) {
            String message = RequestResponseUtils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.error(message, e);

            throw new RuntimeException(message, e);
        }
        System.out.println("found periodical: " + periodical);

        request.setAttribute("periodical", periodical);

        return ApplicationResources.ONE_PERIODICAL_VIEW_NAME;
    }
}
