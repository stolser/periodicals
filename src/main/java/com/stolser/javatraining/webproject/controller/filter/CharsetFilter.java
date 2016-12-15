package com.stolser.javatraining.webproject.controller.filter;

import com.stolser.javatraining.webproject.controller.ApplicationResources;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

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
