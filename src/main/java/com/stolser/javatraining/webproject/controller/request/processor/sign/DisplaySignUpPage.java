package com.stolser.javatraining.webproject.controller.request.processor.sign;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.SIGN_UP_PAGE_VIEW_NAME;

public class DisplaySignUpPage implements RequestProcessor {
    private DisplaySignUpPage() {}

    private static class InstanceHolder {
        private static final DisplaySignUpPage INSTANCE = new DisplaySignUpPage();
    }

    public static DisplaySignUpPage getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("roles", User.Role.values());
        return FORWARD + SIGN_UP_PAGE_VIEW_NAME;
    }
}
