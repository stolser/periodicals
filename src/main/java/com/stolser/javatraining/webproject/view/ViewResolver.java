package com.stolser.javatraining.webproject.view;

import com.stolser.javatraining.webproject.controller.ApplicationResources;

public class ViewResolver {

    public static String getPageByViewName(String viewName) {
        return String.format(ApplicationResources.JSP_VIEW_NAME_RESOLVER_PATTERN, viewName);
    }
}
