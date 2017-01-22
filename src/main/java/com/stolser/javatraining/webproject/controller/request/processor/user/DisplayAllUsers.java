package com.stolser.javatraining.webproject.controller.request.processor.user;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.service.UserService;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_USERS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.USER_LIST_VIEW_NAME;

/**
 * Processes a GET request to a page with a list of all users in the system.
 */
public class DisplayAllUsers implements RequestProcessor {
    private UserService userService = UserServiceImpl.getInstance();

    private DisplayAllUsers() {}

    private static class InstanceHolder {
        private static final DisplayAllUsers INSTANCE = new DisplayAllUsers();
    }

    public static DisplayAllUsers getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ALL_USERS_ATTR_NAME, userService.findAll());
        return Optional.of(USER_LIST_VIEW_NAME);
    }
}
