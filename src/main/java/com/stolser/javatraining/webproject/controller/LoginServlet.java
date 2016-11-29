package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.model.entity.user.Login;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.REQUEST_ORIGINAL_URI;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String redirectUri;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ((username != null) && (password != null)) {
            String passwordHash = getPasswordHash(password);
            UserService userService = UserService.getInstance();

            Login login = userService.findOneLoginByUserName(username);

            if (passwordHash.equals(login.getPasswordHash())) {
                User thisUser = userService.findOneUserByUserName(username);
                request.getSession().setAttribute(CURRENT_USER_ATTR_NAME, thisUser);

                String originalUri = (String) request.getSession().getAttribute(REQUEST_ORIGINAL_URI);
                redirectUri = (originalUri != null) ? originalUri : "/adminPanel";
                request.getSession().removeAttribute(REQUEST_ORIGINAL_URI);

            } else {
                request.getSession().setAttribute("username", username);
                redirectUri = "/login.jsp";
            }

        } else {
            redirectUri = "/login.jsp";
        }

        response.sendRedirect(redirectUri);

    }

    private String getPasswordHash(String password) {
        // todo: implement hashing the password;
        return password;
    }
}
