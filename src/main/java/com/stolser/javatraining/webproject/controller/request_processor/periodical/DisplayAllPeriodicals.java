package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_PERIODICALS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_LIST_VIEW_NAME;

public class DisplayAllPeriodicals implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute(ALL_PERIODICALS_ATTR_NAME, periodicalService.findAll());

        return PERIODICAL_LIST_VIEW_NAME;
    }
}
