package com.stolser.javatraining.webproject.controller.request_processor;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.CustomRedirectException;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOut implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();

        String redirectUri = ApplicationResources.LOGIN_HREF;

        try {
            response.sendRedirect(redirectUri);
            return null;

        } catch (IOException e) {
            String message = HttpUtils.getRedirectionExceptionMessage(request,
                    redirectUri);

            throw new CustomRedirectException(message, e);
        }
    }
}
