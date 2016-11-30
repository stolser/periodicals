package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.CustomSqlException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayAllPeriodicals implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayAllPeriodicals.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        List<Periodical> allPeriodicals;
        try {
            allPeriodicals = PeriodicalService.getInstance().findAll();

        } catch (CustomSqlException e) {
            String message = Utils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.debug(message, e);

            throw new RuntimeException(message);
        }

        request.setAttribute("allPeriodicals", allPeriodicals);

        return "periodicals/periodicalList";
    }
}
