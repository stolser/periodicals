package com.stolser.javatraining.webproject.controller.request.processor;

import com.stolser.javatraining.webproject.controller.FrontController;

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
