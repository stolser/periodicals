package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDiscardedPeriodicals implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteDiscardedPeriodicals.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        try {
            PeriodicalService.getInstance().deleteAllDiscarded();
            response.sendRedirect(ApplicationResources.PERIODICAL_LIST_HREF);

        } catch (Exception e) {
            String message = Utils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.error(message, e);

            throw new RuntimeException(message, e);
        }

        return null;
    }
}
