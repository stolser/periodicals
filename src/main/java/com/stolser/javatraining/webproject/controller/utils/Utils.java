package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MESSAGES_ATTR_NAME;

public class Utils {
    public static long getUserIdFromSession(HttpServletRequest request) {
        User user = (User) request.getSession()
                .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);

        return (user != null) ? user.getId() : 0;
    }

    public static User getCurrentUserFromFromDb(HttpServletRequest request) {
        long userId = getUserIdFromSession(request);

        return UserService.getInstance().findOneById(userId);
    }

    public static String getExceptionMessageForRequestProcessor(HttpServletRequest request, Exception e) {
        String message = String.format("User id = %d. " +
                "Original: %s. ", Utils.getUserIdFromSession(request), e.getMessage());

        return message;
    }

    public static Periodical getPeriodicalFromRequest(HttpServletRequest request) {
        Periodical periodical = new Periodical();

        System.out.println("Periodical from a request:");
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

    public static int getFirstIdFromUri(String uri) {
        Matcher matcher = Pattern.compile("\\d+").matcher(uri);

        if (matcher.find()) {
            String stringId = matcher.group();

            return Integer.valueOf(stringId);

        }

        throw new IllegalArgumentException(String.format("Uri (%s) must contain id.", uri));
    }

    public static void addMessagesToSession(HttpServletRequest request,
                                            Map<String, List<FrontendMessage>> frontMessageMap) {
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, frontMessageMap);
    }

    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                                      String redirectUri) {
        try {
            response.sendRedirect(redirectUri);

        } catch (Exception e) {
            String message = String.format("User id = %d. Exception during redirection to '%s'.",
                    Utils.getUserIdFromSession(request), redirectUri);

            throw new RuntimeException(message, e);
        }
    }

}
