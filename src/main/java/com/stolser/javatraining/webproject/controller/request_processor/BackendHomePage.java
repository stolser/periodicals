package com.stolser.javatraining.webproject.controller.request_processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.BACKEND_MAIN_PAGE_VIEW_NAME;

/**
 * Processes a GET request to a backend main page.
 */
public class BackendHomePage implements RequestProcessor {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        return BACKEND_MAIN_PAGE_VIEW_NAME;
    }
}