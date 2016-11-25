package com.stolser.javatraining.webproject.controller.command.user;

import com.stolser.javatraining.webproject.controller.command.RequestCommand;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayAllUsers implements RequestCommand {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        List<User> allUsers = UserService.getInstance().findAll();

        request.setAttribute("allUsers", allUsers);

        return "/WEB-INF/admin/user/usersList.jsp";
    }
}
