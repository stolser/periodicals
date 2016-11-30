package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
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
                "Original: $s. ", Utils.getUserIdFromRequest(request), e.getMessage());

        return message;
    }
}
