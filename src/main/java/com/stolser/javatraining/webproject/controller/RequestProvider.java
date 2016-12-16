package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;

import javax.servlet.http.HttpServletRequest;

public interface RequestProvider {
    RequestProcessor getRequestProcessor(HttpServletRequest request);
}
