package com.stolser.javatraining.webproject.controller.request.processor.sign;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.LOGIN_PAGE;

/**
 * Processes a GET request to sing out the current user and redirects to a 'login' page.
 */
public class SignOut implements RequestProcessor {

    private SignOut() {}

    private static class InstanceHolder {
        private static final SignOut INSTANCE = new SignOut();
    }

    public static SignOut getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();

        HttpUtils.sendRedirect(request, response, LOGIN_PAGE);

        return Optional.empty();
    }
}
