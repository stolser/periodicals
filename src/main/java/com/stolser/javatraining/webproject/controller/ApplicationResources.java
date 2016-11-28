package com.stolser.javatraining.webproject.controller;

public class ApplicationResources {
    public static final String MESSAGE_ATTRIBUTE = "messageAttr";
    public static final String CURRENT_USER_ATTR_NAME = "thisUser";
    public static final String REQUEST_ORIGINAL_URI = "originalUri";


    public static String getErrorViewName(Exception exception) {
        return "errors/page-404";
    }
}
