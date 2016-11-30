package com.stolser.javatraining.webproject.controller.command.user;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayAllUsers implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<User> allUsers = UserService.getInstance().findAll();

        request.setAttribute("allUsers", allUsers);

        return "users/userList";
    }
}
