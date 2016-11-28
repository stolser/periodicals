package com.stolser.javatraining.webproject.view;

public class ViewResolver {

    public static String getPageByViewName(String viewName) {
        return String.format("/WEB-INF/admin/%s.jsp", viewName);
    }
}
