package com.stolser.javatraining.webproject.view;

/**
 * Contains methods for converting logical names into paths to concrete files on the server.
 */
public final class ViewResolver {
    private static final String JSP_PRIVATE_RESOURCE_PATH_PATTERN = "/WEB-INF/backend/%s.jsp";
    private static final String JSP_PUBLIC_RESOURCE_PATH_PATTERN = "/%s.jsp";

    private ViewResolver() {}

    /**
     * Returns a path to a file that will generate html content of a private page to be sent to the client.
     * @param viewName a logical name of a private resource access to which
     *                 requires authentication and authorization
     */
    public static String resolvePrivateViewName(String viewName) {
        return String.format(JSP_PRIVATE_RESOURCE_PATH_PATTERN, viewName);
    }

    /**
     * Returns a path to a file that will generate html content of a public page to be sent to the client.
     * @param viewName a logical name of a public resource access to which does not require any authentication
     */
    public static String resolvePublicViewName(String viewName) {
        return String.format(JSP_PUBLIC_RESOURCE_PATH_PATTERN, viewName);
    }
}
