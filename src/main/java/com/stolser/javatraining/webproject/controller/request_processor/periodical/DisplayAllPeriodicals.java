package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_PERIODICALS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_LIST_VIEW_NAME;

public class DisplayAllPeriodicals implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<Periodical> allPeriodicals = PeriodicalServiceImpl.getInstance().findAll();
        request.setAttribute(ALL_PERIODICALS_ATTR_NAME, allPeriodicals);

        return PERIODICAL_LIST_VIEW_NAME;
    }
}
