package com.stolser.javatraining.webproject.controller.encoding;

import com.stolser.javatraining.webproject.controller.ApplicationResources;

import javax.servlet.*;
import java.io.IOException;

/**
 * Allows entering on the frontend and saving cyrillic symbols in the system.
 */
public class CharsetEncoder implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(ApplicationResources.CHARACTER_ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
