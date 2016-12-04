package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static long getUserIdFromRequest(HttpServletRequest request) {
        User user = (User) request.getSession()
                .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);

        return user.getId();
    }

    public static String getExceptionMessageForRequestProcessor(HttpServletRequest request, Exception e) {
        String message = String.format("User id = %d. " +
                "Original: %s. ", Utils.getUserIdFromRequest(request), e.getMessage());

        return message;
    }

    public static Periodical getPeriodicalFromRequest(HttpServletRequest request) {
        Periodical periodical = new Periodical();

        System.out.println("periodicalId = " + request.getParameter("entityId"));
        System.out.println("periodicalName = " + request.getParameter("periodicalName"));
        System.out.println("periodicalCategory = " + request.getParameter("periodicalCategory"));
        System.out.println("periodicalPublisher = " + request.getParameter("periodicalPublisher"));
        System.out.println("periodicalDescription = " + request.getParameter("periodicalDescription").trim());
        System.out.println("periodicalCost = " + request.getParameter("periodicalCost"));
        System.out.println("periodicalStatus = " + request.getParameter("periodicalStatus"));

        periodical.setId(Long.valueOf(request.getParameter("entityId")));
        periodical.setName(request.getParameter("periodicalName"));
        periodical.setCategory(request.getParameter("periodicalCategory"));
        periodical.setPublisher(request.getParameter("periodicalPublisher"));
        periodical.setDescription(request.getParameter("periodicalDescription").trim());
        periodical.setOneMonthCost(Double.valueOf(request.getParameter("periodicalCost")));
        periodical.setStatus(Periodical.Status.valueOf((request.getParameter("periodicalStatus")).toUpperCase()));

        return periodical;
    }

}
