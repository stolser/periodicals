package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.command.RequestCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MESSAGE_ATTRIBUTE;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.getErrorPage;

@WebServlet(urlPatterns = {"/admin/*"})
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
        String page;

        String requestURI = request.getRequestURI();
        System.out.println("processRequest(): requestURI = " + requestURI);

        try {
            RequestHelper helper = new RequestHelper(request);
            RequestCommand command = helper.getCommand();

            System.out.println("command = " + command);

            page = command.process(request, response);

        } catch (Exception e) {
            LOGGER.debug("Exception during request processing: {}", e.getMessage());
            request.setAttribute(MESSAGE_ATTRIBUTE, e.getLocalizedMessage());

            page = getErrorPage(e);
        }

        dispatch(page, request, response);
    }

    private void dispatch(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}
