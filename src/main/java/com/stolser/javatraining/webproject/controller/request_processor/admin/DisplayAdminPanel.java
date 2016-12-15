package com.stolser.javatraining.webproject.controller.request_processor.admin;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayAdminPanel implements RequestProcessor {
    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        return ApplicationResources.ADMIN_PANEL_VIEW_NAME;
    }
}
