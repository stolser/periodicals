package com.stolser.javatraining.webproject.view;

/**
 * Contains methods for converting logical names into paths to concrete files on the server.
 */
public final class JspViewResolver implements ViewResolver {
    private static final String JSP_PRIVATE_RESOURCE_PATH_PATTERN = "/WEB-INF/backend/%s.jsp";
    private static final String JSP_PUBLIC_RESOURCE_PATH_PATTERN = "/%s.jsp";

    private JspViewResolver() {}

    private static class InstanceHolder {
        private static final JspViewResolver INSTANCE = new JspViewResolver();
    }

    public static JspViewResolver getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String resolvePrivateViewName(String viewName) {
        return String.format(JSP_PRIVATE_RESOURCE_PATH_PATTERN, viewName);
    }


    @Override
    public String resolvePublicViewName(String viewName) {
        return String.format(JSP_PUBLIC_RESOURCE_PATH_PATTERN, viewName);
    }
}
