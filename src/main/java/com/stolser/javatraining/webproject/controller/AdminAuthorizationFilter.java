package com.stolser.javatraining.webproject.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminAuthorizationFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthorizationFilter.class);
    private static final String PERIODICAL_EDIT_URL = "periodical/edit.jsp";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().oneByType(Map.class).get("username");
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        System.out.println("requestURI = " + requestURI);

        if (subject.hasRole("admin")) {
            chain.doFilter(request, response);
        } else {
            if ((requestURI != null) && (requestURI.contains(PERIODICAL_EDIT_URL))) {
                LOGGER.debug(String.format("Access denied for user '%s'!!!\n", username));
                subject.getSession().setAttribute("username", username);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("/accessDenied.jsp");
            } else {
                chain.doFilter(request, response);
            }

        }
    }

    @Override
    public void destroy() {

    }
}