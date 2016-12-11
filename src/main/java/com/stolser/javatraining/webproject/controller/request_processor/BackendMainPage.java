package com.stolser.javatraining.webproject.controller.request_processor;

import com.stolser.javatraining.webproject.controller.ApplicationResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackendMainPage implements RequestProcessor {

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        return ApplicationResources.BACKEND_MAIN_PAGE_VIEW_NAME;
    }
}