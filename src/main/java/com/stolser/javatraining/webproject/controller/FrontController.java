package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProvider;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProviderImpl;
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

/**
 * Implementation of the Front Controller pattern.
 */
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
    private static final String USER_ID_REQUEST_URI = "User id = {}. requestURI = {}";
    private RequestProvider requestProvider = RequestProviderImpl.getInstance();

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
        try {
            String viewName = requestProvider.getRequestProcessor(request).process(request, response);
            dispatch(viewName, request, response);

        } catch (RuntimeException e) {
            LOGGER.error(USER_ID_REQUEST_URI,
                    HttpUtils.getUserIdFromSession(request), request.getRequestURI(), e);

            HttpUtils.sendRedirect(request, response,
                    ViewResolver.getPublicResourceByViewName(HttpUtils.getErrorViewName(e)));
        }
    }

    private void dispatch(String viewName, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (viewName != null) {
            String page = ViewResolver.getPrivateResourceByViewName(viewName);

            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
