package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request.processor.DispatchException;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProvider;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProviderImpl;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stolser.javatraining.webproject.view.ViewResolver.resolvePrivateViewName;
import static com.stolser.javatraining.webproject.view.ViewResolver.resolvePublicViewName;

/**
 * Implementation of the Front Controller pattern.
 */
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
    private static final String USER_ID_REQUEST_URI = "User id = {}. requestURI = {}";
    private static final String DISPATCHING_TO_THE_VIEW_NAME = "Dispatching to the viewName = '%s'.";
    private final RequestProvider requestProvider = RequestProviderImpl.getInstance();

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
            requestProvider
                    .getRequestProcessor(request)
                    .process(request, response)
                    .ifPresent(viewName -> dispatch(viewName, request, response));

        } catch (RuntimeException e) {
            logExceptionAndRedirectToErrorPage(request, response, e);
        }
    }

    private void dispatch(String viewName, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(resolvePrivateViewName(viewName));
            dispatcher.forward(request, response);

        } catch (ServletException | IOException e) {
            throw new DispatchException(String.format(DISPATCHING_TO_THE_VIEW_NAME, viewName), e);
        }
    }

    private void logExceptionAndRedirectToErrorPage(HttpServletRequest request, HttpServletResponse response,
                                                    RuntimeException e) {
        LOGGER.error(USER_ID_REQUEST_URI,
                HttpUtils.getUserIdFromSession(request), request.getRequestURI(), e);

        HttpUtils.sendRedirect(request, response,
                resolvePublicViewName(HttpUtils.getErrorViewName(e)));
    }
}
