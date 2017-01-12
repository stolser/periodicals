package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_PERIODICALS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_LIST_VIEW_NAME;

/**
 * Processes a GET request to a page displaying a list of periodicals.<br/>
 * A user with role = 'subscriber' will see only those that have status = 'active'. <br/>
 * A user with role = 'admin' will see all periodicals in the system.
 */
public class DisplayAllPeriodicals implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    private DisplayAllPeriodicals() {}

    private static class InstanceHolder {
        private static final DisplayAllPeriodicals INSTANCE = new DisplayAllPeriodicals();
    }

    public static DisplayAllPeriodicals getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ALL_PERIODICALS_ATTR_NAME, periodicalService.findAll());
        return PERIODICAL_LIST_VIEW_NAME;
    }
}
