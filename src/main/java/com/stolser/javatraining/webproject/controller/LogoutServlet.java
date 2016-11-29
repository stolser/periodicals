package com.stolser.javatraining.webproject.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession().removeAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();
        response.sendRedirect("/login.jsp");
    }
}
