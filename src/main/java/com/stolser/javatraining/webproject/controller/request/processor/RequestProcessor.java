package com.stolser.javatraining.webproject.controller.request.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestProcessor {
    String FORWARD = "forward:";
    String REDIRECT = "redirect:";
    String NO_ACTION = "noAction:noUri";
    /**
     * Processes a current http request. Can update session or request attributes, analyse request
     * parameters, generate frontend messages.
     * @return a basic view name of the page where this request should be forwarded
     *      or {@code empty object} if a request was redirected to another uri and it does not
     *      require to be forwarded.
     */
    String process(HttpServletRequest request, HttpServletResponse response);
}
