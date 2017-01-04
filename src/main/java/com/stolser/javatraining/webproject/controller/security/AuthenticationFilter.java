package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Makes sure that this request comes from a signed in user and the session has not expired.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(doesRequestNotRequireAuthentication(request)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        String requestURI = request.getRequestURI();
        User thisUser = HttpUtils.getCurrentUserFromFromDb(request);

        if (thisUser == null) {
            request.getSession().setAttribute(ORIGINAL_URI_ATTR_NAME, requestURI);
            response.sendRedirect(SIGN_IN_URI);

        } else if (!User.Status.ACTIVE.equals(thisUser.getStatus())) {
            response.sendRedirect(SIGN_OUT_URI);

        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean doesRequestNotRequireAuthentication(HttpServletRequest request) {
        List<String> unProtectedUris = Arrays.asList("/backend/signIn", "/backend/signUp");
        String requestUri = request.getRequestURI();

        return unProtectedUris.contains(requestUri);
    }

    @Override
    public void destroy() {}
}
