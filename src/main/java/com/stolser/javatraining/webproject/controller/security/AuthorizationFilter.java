package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks whether a current user has enough permissions to get a requested resource or perform
 * an operation.
 */
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
    private static final String ACCESS_DENIED_FOR_USER = "Access denied for user '%s' to '%s'!!!%n";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Proceeds to the next resource if a current user has enough permissions, and
     * redirects to 'access denied page' otherwise.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestUri = ((HttpServletRequest) request).getRequestURI();

        if (isRequestAuthorized(request)) {
            chain.doFilter(request, response);

        } else {
            String username = ((User)((HttpServletRequest) request).getSession()
                    .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME)).getUserName();

            LOGGER.error(String.format(ACCESS_DENIED_FOR_USER,
                    username, requestUri));

            ((HttpServletResponse) response).sendRedirect(ApplicationResources.ACCESS_DENIED_URI);
        }
    }

    private boolean isRequestAuthorized(ServletRequest request) {
        return Authorization.getInstance().checkPermissions((HttpServletRequest) request);
    }

    @Override
    public void destroy() {}
}