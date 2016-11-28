package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.auth.Authorization;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = {"/admin/*"})
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if (requestIsAuthorized(request)) {
            System.out.println("permission is granted to '" + requestURI + "'");
            chain.doFilter(request, response);

        } else {
            String username = ((User)((HttpServletRequest) request).getSession()
                    .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME)).getUserName();

            LOGGER.debug(String.format("Access denied for user '%s' to '%s'!!!\n",
                    username, requestURI));

            ((HttpServletResponse) response).sendRedirect("/accessDenied.jsp");

        }
    }

    private boolean requestIsAuthorized(ServletRequest request) {
        return new Authorization((HttpServletRequest) request).checkPermissions();
    }

    @Override
    public void destroy() {

    }
}