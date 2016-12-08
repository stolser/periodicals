package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        User thisUser = Utils.getCurrentUserFromFromDb(request);

        if (thisUser == null) {
            request.getSession().setAttribute(ApplicationResources.ORIGINAL_URI_ATTR_NAME, requestURI);
            response.sendRedirect("/login.jsp");

        } else if (!User.Status.ACTIVE.equals(thisUser.getStatus())) {
            response.sendRedirect("/Logout");

        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {}
}
