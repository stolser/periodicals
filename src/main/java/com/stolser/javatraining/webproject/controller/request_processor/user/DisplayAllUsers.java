package com.stolser.javatraining.webproject.controller.request_processor.user;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.service.UserService;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_USERS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.USER_LIST_VIEW_NAME;

public class DisplayAllUsers implements RequestProcessor {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute(ALL_USERS_ATTR_NAME, userService.findAll());

        return USER_LIST_VIEW_NAME;
    }
}
