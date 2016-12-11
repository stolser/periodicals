package com.stolser.javatraining.webproject.controller;

import java.util.Locale;

public class ApplicationResources {
    public static final String MESSAGES_ATTR_NAME = "messages";
    public static final String GENERAL_MESSAGES_FRONT_BLOCK_NAME = "topMessages";
    public static final String MESSAGE_ATTRIBUTE = "messageAttr";
    public static final String CURRENT_USER_ATTR_NAME = "thisUser";
    public static final String ORIGINAL_URI_ATTR_NAME = "originalUri";

    public static final String VALIDATION_BUNDLE_PATH = "webProject/i18n/validation";
    public static final String PERIODICAL_LIST_VIEW_NAME = "periodicals/periodicalList";
    public static final String ONE_PERIODICAL_VIEW_NAME = "periodicals/onePeriodical";
    public static final String CREATE_EDIT_PERIODICAL_VIEW_NAME = "periodicals/createAndEdit";

    public static final String USER_LIST_VIEW_NAME = "users/userList";
    public static final String ONE_USER_INFO_VIEW_NAME = "users/oneUserInfo";
    public static final String PERIODICAL_LIST_HREF = "/backend/periodicals";

    public static final String CURRENT_USER_ACCOUNT_HREF = "/backend/users/currentUser";
    public static final String PERIODICAL_CREATE_NEW_HREF = "/backend/periodicals/createNew";
    public static final String PERIODICAL_UPDATE_HREF = "/backend/periodicals/update";
    public static final String PERIODICAL_DELETE_DISCARDED = "/backend/periodicals/discarded/delete";
    public static final String PERIODICAL_CREATE_UPDATE_REST = "/backend/periodicals";
    public static final String ADMIN_ROLE_NAME = "admin";
    public static final String SUBSCRIBER_ROLE_NAME = "subscriber";
    public static final String GUEST_ROLE_NAME = "guest";

    public static final String SIGN_IN_USERNAME_PARAM_NAME = "signInUsername";
    public static final String PERIODICAL_NAME_PARAM_NAME = "periodicalName";
    public static final String PERIODICAL_CATEGORY_PARAM_NAME = "periodicalCategory";
    public static final String PERIODICAL_PUBLISHER_PARAM_NAME = "periodicalPublisher";
    public static final String PERIODICAL_COST_PARAM_NAME = "periodicalCost";


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
