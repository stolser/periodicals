package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.auth.Authorization;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;
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
    public static final String CURRENT_USER_ATTR_NAME = "thisUser";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().oneByType(Map.class).get("username");
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        System.out.println("requestURI = " + requestURI);

        User user = UserService.getInstance().findOneByUserName(username);
        ((HttpServletRequest) request).getSession().setAttribute(CURRENT_USER_ATTR_NAME, user);
        System.out.println("current user = " + user);

        Authorization auth = new Authorization((HttpServletRequest) request);

        if (auth.checkPermissions()) {
            System.out.println("permission is granted to '" + requestURI + "'");
            chain.doFilter(request, response);

        } else {
            LOGGER.debug(String.format("Access denied for user '%s'!!!\n", username));
            ((HttpServletResponse) response).sendRedirect("/accessDenied.jsp");

        }
    }

    @Override
    public void destroy() {

    }
}