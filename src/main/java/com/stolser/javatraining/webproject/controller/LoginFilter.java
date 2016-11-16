package com.stolser.javatraining.webproject.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {"/userLogin"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        String password = request.getParameter("password");
        if (password.equals("admin")) {
            chain.doFilter(request, response);  //sends request to next resource
        } else {
            out.print("username or password error!");
            RequestDispatcher rd = request.getRequestDispatcher("index.html");
            rd.include(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
