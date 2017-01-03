package com.stolser.javatraining.webproject.view;

/**
 * Contains methods for converting logical names into paths to concrete files on the server.
 */
public class ViewResolver {
    public static final String JSP_PRIVATE_RESOURCE_PATH_PATTERN = "/WEB-INF/backend/%s.jsp";
    public static final String JSP_PUBLIC_RESOURCE_PATH_PATTERN = "/%s.jsp";
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
