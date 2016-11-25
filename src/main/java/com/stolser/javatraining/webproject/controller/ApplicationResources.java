package com.stolser.javatraining.webproject.controller;

public class ApplicationResources {
    public static final String MESSAGE_ATTRIBUTE = "messageAttr";

    public static String getErrorPage(Exception exception) {
        return "/page-404.jsp";
    }
}
