package com.stolser.javatraining.webproject.view;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.JSP_PRIVATE_RESOURCE_PATH_PATTERN;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.JSP_PUBLIC_RESOURCE_PATH_PATTERN;

/**
 * Contains methods for converting logical names into paths to concrete files on the server.
 */
public class ViewResolver {
    /**
     * @param viewName a logical name of a private resource access to which
     *                 requires authentication and authorization
     * @return a path to a file that will generate html content to be sent to the client
     */
    public static String getPrivateResourceByViewName(String viewName) {
        return String.format(JSP_PRIVATE_RESOURCE_PATH_PATTERN, viewName);
    }

    /**
     * @param viewName a logical name of a public resource access to which does not require any authentication
     * @return a path to a file that will generate html content to be sent to the client
     */
    public static String getPublicResourceByViewName(String viewName) {
        return String.format(JSP_PUBLIC_RESOURCE_PATH_PATTERN, viewName);
    }
}
