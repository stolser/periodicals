package com.stolser.javatraining.webproject.controller;

import java.util.Locale;

public class ApplicationResources {
    public static final String MESSAGE_ATTRIBUTE = "messageAttr";
    public static final String CURRENT_USER_ATTR_NAME = "thisUser";
    public static final String ORIGINAL_URI_ATTR_NAME = "originalUri";
    public static final String VALIDATION_BUNDLE_PATH = "webProject/i18n/validation";


    public static String getErrorViewName(Exception exception) {
        return "errors/page-404";
    }

    public enum SystemLocale {
        EN_EN(Locale.ENGLISH),
        RU_RU(new Locale("ru", "RU")),
        UK_UA(new Locale("uk", "UA"));

        private Locale locale;

        SystemLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
