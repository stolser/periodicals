package com.stolser.javatraining.webproject.view;

public interface ViewResolver {
    /**
     * Returns a path to a file that will generate html content of a private page to be sent to the client.
     *
     * @param viewName a logical name of a private resource access to which
     *                 requires authentication and authorization
     */
    String resolvePrivateViewName(String viewName);

    /**
     * Returns a path to a file that will generate html content of a public page to be sent to the client.
     *
     * @param viewName a logical name of a public resource access to which does not require any authentication
     */
    String resolvePublicViewName(String viewName);
}
