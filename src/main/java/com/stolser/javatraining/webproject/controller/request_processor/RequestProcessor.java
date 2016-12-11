package com.stolser.javatraining.webproject.controller.request_processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestProcessor {
    String getViewName(HttpServletRequest request, HttpServletResponse response);
}
