package com.stolser.javatraining.webproject.controller.request.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.BACKEND_MAIN_PAGE_VIEW_NAME;

/**
 * Processes a GET request to a backend main page.
 */
public class DisplayBackendHomePage implements RequestProcessor {
    private DisplayBackendHomePage() {}

    private static class InstanceHolder {
        private static final DisplayBackendHomePage INSTANCE = new DisplayBackendHomePage();
    }

    public static DisplayBackendHomePage getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        return Optional.of(BACKEND_MAIN_PAGE_VIEW_NAME);
    }
}