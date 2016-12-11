package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MESSAGE_ATTRIBUTE;

//@WebServlet(urlPatterns = {"/admin/*"})
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String viewName;

        String requestURI = request.getRequestURI();
        System.out.println("processRequest(): requestURI = " + requestURI);

        try {
            RequestProcessor processor = new RequestProvider(request).getRequestProcessor();

            viewName = processor.getViewName(request, response);

        } catch (Exception e) {
            LOGGER.error("User id = {}. requestURI = {}",
                    HttpUtils.getUserIdFromSession(request), requestURI, e);
            request.setAttribute(MESSAGE_ATTRIBUTE, e.getLocalizedMessage());

            viewName = ApplicationResources.getErrorViewName(e);
        }

        dispatch(viewName, request, response);
    }

    private void dispatch(String viewName, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (viewName != null) {
            String page = ViewResolver.getPageByViewName(viewName);

            System.out.println("forwarding to '" + page + "'");

            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

}
