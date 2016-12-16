package com.stolser.javatraining.webproject.controller.request_processor.admin;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.periodical.statistics.NumberByCategory;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Processes a request to the Admin Panel page.
 */
public class DisplayAdminPanel implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<NumberByCategory> periodicalStatistics = PeriodicalServiceImpl.getInstance()
                .getQuantitativeStatistics();

        request.setAttribute(ApplicationResources.PERIODICAL_STATISTICS_ATTR_NAME, periodicalStatistics);

        return ApplicationResources.ADMIN_PANEL_VIEW_NAME;
    }
}
