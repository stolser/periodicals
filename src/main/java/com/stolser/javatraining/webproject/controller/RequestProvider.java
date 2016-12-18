package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * Used by {@link FrontController} to move each request processing logic into separate classes.
 */
public interface RequestProvider {
    /**
     * @return a specific request processing implementation of the {@code RequestProcessor} interface
     */
    RequestProcessor getRequestProcessor(HttpServletRequest request);
}
