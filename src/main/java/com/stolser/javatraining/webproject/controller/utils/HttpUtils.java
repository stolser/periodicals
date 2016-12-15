package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class HttpUtils {

    private static final String REDIRECTION_FROM_TO_TEXT = "During redirection from \"%s\" to \"%s\"";
    private static final String URI_MUST_CONTAIN_ID_TEXT = "Uri (%s) must contain id.";
    private static final String EXCEPTION_DURING_REDIRECTION_TEXT = "User id = %d. Exception during redirection to '%s'.";

    public static long getUserIdFromSession(HttpServletRequest request) {
        User user = (User) request.getSession()
                .getAttribute(CURRENT_USER_ATTR_NAME);

        return (user != null) ? user.getId() : 0;
    }

    public static User getCurrentUserFromFromDb(HttpServletRequest request) {
        long userId = getUserIdFromSession(request);

        return UserServiceImpl.getInstance().findOneById(userId);
    }

    public static String getRedirectionExceptionMessage(HttpServletRequest request,
                                                       String destination) {

        return String.format(REDIRECTION_FROM_TO_TEXT,
                request.getRequestURI(), destination);
    }

    public static Periodical getPeriodicalFromRequest(HttpServletRequest request) {
        Periodical periodical = new Periodical();
//
//        System.out.println("Periodical from a request:");
//        System.out.println("periodicalId = " + request.getParameter(ENTITY_ID_PARAM_NAME));
//        System.out.println("periodicalName = " + request.getParameter(PERIODICAL_NAME_PARAM_NAME).toUpperCase());
//        System.out.println("periodicalCategory = " + request.getParameter("periodicalCategory"));
//        System.out.println("periodicalPublisher = " + request.getParameter("periodicalPublisher"));
//        System.out.println("periodicalDescription = " + request.getParameter("periodicalDescription").trim());
//        System.out.println("periodicalCost = " + request.getParameter("periodicalCost"));
//        System.out.println("periodicalStatus = " + request.getParameter("periodicalStatus"));

        periodical.setId(Long.valueOf(request.getParameter(ENTITY_ID_PARAM_NAME)));
        periodical.setName(request.getParameter(PERIODICAL_NAME_PARAM_NAME));
        periodical.setCategory(PeriodicalCategory.valueOf(
                request.getParameter(PERIODICAL_CATEGORY_PARAM_NAME).toUpperCase()));
        periodical.setPublisher(request.getParameter(PERIODICAL_PUBLISHER_PARAM_NAME));
        periodical.setDescription(request.getParameter(PERIODICAL_DESCRIPTION_PARAM_NAME).trim());
        periodical.setOneMonthCost(Double.valueOf(request.getParameter(PERIODICAL_COST_PARAM_NAME)));
        periodical.setStatus(Periodical.Status.valueOf(
                (request.getParameter(PERIODICAL_STATUS_PARAM_NAME)).toUpperCase()));

        return periodical;
    }

    public static int getFirstIdFromUri(String uri) {
        Matcher matcher = Pattern.compile("\\d+").matcher(uri);

        if (matcher.find()) {
            String stringId = matcher.group();

            return Integer.valueOf(stringId);

        }

        throw new IllegalArgumentException(String.format(URI_MUST_CONTAIN_ID_TEXT, uri));
    }

    public static void addMessagesToSession(HttpServletRequest request,
                                            Map<String, List<FrontendMessage>> frontMessageMap) {
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, frontMessageMap);
    }

    public static void addGeneralMessagesToSession(HttpServletRequest request,
                                                   List<FrontendMessage> generalMessages) {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(GENERAL_MESSAGES_FRONT_BLOCK_NAME, generalMessages);
        HttpUtils.addMessagesToSession(request, frontMessageMap);
    }

    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                                      String redirectUri) {
        try {
            response.sendRedirect(redirectUri);

        } catch (Exception e) {
            String message = String.format(EXCEPTION_DURING_REDIRECTION_TEXT,
                    HttpUtils.getUserIdFromSession(request), redirectUri);

            throw new RuntimeException(message, e);
        }
    }

}
