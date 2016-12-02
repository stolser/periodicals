package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersistOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOnePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Periodical periodicalToSave;

        try {
            periodicalToSave = getPeriodicalFromRequest(request);
            PeriodicalService.getInstance().save(periodicalToSave);

        } catch (Exception e) {
            String message = Utils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.debug(message, e);

            throw new RuntimeException(message, e);
        }

        System.out.println("Persisted periodical: " + periodicalToSave);

        return new DisplayAllPeriodicals().getViewName(request, response);
    }

    private Periodical getPeriodicalFromRequest(HttpServletRequest request) {
        Periodical periodical = new Periodical();

        System.out.println("periodicalName = " + request.getParameter("periodicalName"));
        System.out.println("periodicalCategory = " + request.getParameter("periodicalCategory"));
        System.out.println("periodicalPublisher = " + request.getParameter("periodicalPublisher"));
        System.out.println("periodicalDescription = " + request.getParameter("periodicalDescription").trim());
        System.out.println("periodicalCost = " + request.getParameter("periodicalCost"));
        System.out.println("periodicalStatus = " + request.getParameter("periodicalStatus"));

        periodical.setName(request.getParameter("periodicalName"));
        periodical.setCategory(request.getParameter("periodicalCategory"));
        periodical.setPublisher(request.getParameter("periodicalPublisher"));
        periodical.setDescription(request.getParameter("periodicalDescription").trim());
        periodical.setOneMonthCost(Double.valueOf(request.getParameter("periodicalCost")));
        periodical.setStatus(Periodical.Status.valueOf((request.getParameter("periodicalStatus")).toUpperCase()));

        return periodical;
    }
}
