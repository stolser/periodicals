package com.stolser.javatraining.webproject.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        System.out.println("AuthenticationFilter: requestURI = " + requestURI);

        HttpSession session = request.getSession();

        if (session.getAttribute(CURRENT_USER_ATTR_NAME) == null) {

            session.setAttribute(ApplicationResources.ORIGINAL_URI_ATTR_NAME, requestURI);

            System.out.println("redirect to login.jsp (requestURI = " + requestURI + ")");
            response.sendRedirect("/login.jsp");

        } else {
            System.out.println("thisUser = " + session.getAttribute(CURRENT_USER_ATTR_NAME));
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
