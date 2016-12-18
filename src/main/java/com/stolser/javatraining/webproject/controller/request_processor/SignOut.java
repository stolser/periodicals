package com.stolser.javatraining.webproject.controller.request_processor;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.SIGN_IN_URI;

/**
 * Processes a GET request to sing out the current user and redirects to a 'login' page.
 */
public class SignOut implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();

        String redirectUri = SIGN_IN_URI;
        HttpUtils.sendRedirect(request, response, redirectUri);

        return null;
    }
}
