package com.stolser.javatraining.webproject.controller.request_processor.user;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ALL_USERS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.USER_LIST_VIEW_NAME;

public class DisplayAllUsers implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<User> allUsers = UserServiceImpl.getInstance().findAll();

        request.setAttribute(ALL_USERS_ATTR_NAME, allUsers);

        return USER_LIST_VIEW_NAME;
    }
}
