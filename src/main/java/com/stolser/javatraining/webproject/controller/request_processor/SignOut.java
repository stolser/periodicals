package com.stolser.javatraining.webproject.controller.request_processor;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOut implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();

        String redirectUri = ApplicationResources.LOGIN_HREF;

        HttpUtils.sendRedirect(request, response, redirectUri);
        return null;
    }
}
