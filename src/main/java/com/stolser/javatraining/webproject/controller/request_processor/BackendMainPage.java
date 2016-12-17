package com.stolser.javatraining.webproject.controller.request_processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.BACKEND_MAIN_PAGE_VIEW_NAME;

public class BackendMainPage implements RequestProcessor {
    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        return BACKEND_MAIN_PAGE_VIEW_NAME;
    }
}