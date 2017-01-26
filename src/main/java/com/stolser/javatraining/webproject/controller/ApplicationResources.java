package com.stolser.javatraining.webproject.controller;

public class ApplicationResources {
    public static final String CHARACTER_ENCODING = "UTF-8";

    public static final String MESSAGES_ATTR_NAME = "messages";
    public static final String GENERAL_MESSAGES_FRONT_BLOCK_NAME = "topMessages";
    public static final String CURRENT_USER_ATTR_NAME = "currentUser";
    public static final String PERIODICAL_ATTR_NAME = "periodical";
    public static final String PERIODICAL_STATUSES_ATTR_NAME = "periodicalStatuses";
    public static final String PERIODICAL_CATEGORIES_ATTR_NAME = "periodicalCategories";
    public static final String ORIGINAL_URI_ATTR_NAME = "originalUri";
    public static final String USERNAME_ATTR_NAME = "username";
    public static final String USER_ROLE_ATTR_NAME = "userRole";
    public static final String USER_EMAIL_ATTR_NAME = "userEmail";
    public static final String ALL_PERIODICALS_ATTR_NAME = "allPeriodicals";
    public static final String ALL_USERS_ATTR_NAME = "allUsers";
    public static final String LANGUAGE_ATTR_NAME = "language";
    public static final String PERIODICAL_STATISTICS_ATTR_NAME = "periodicalStatistics";
    public static final String FINANCIAL_STATISTICS_ATTR_NAME = "financialStatistics";

    public static final String PARAM_NAME = "paramName";
    public static final String PARAM_VALUE = "paramValue";
    public static final String SIGN_IN_USERNAME_PARAM_NAME = "signInUsername";
    public static final String SIGN_UP_USERNAME_PARAM_NAME = "signUpUsername";
    public static final String USER_PASSWORD_PARAM_NAME = "password";
    public static final String USER_REPEAT_PASSWORD_PARAM_NAME = "repeatPassword";
    public static final String PERIODICAL_ID_PARAM_NAME = "periodicalId";
    public static final String PERIODICAL_NAME_PARAM_NAME = "periodicalName";
    public static final String PERIODICAL_CATEGORY_PARAM_NAME = "periodicalCategory";
    public static final String PERIODICAL_PUBLISHER_PARAM_NAME = "periodicalPublisher";
    public static final String PERIODICAL_COST_PARAM_NAME = "periodicalCost";
    public static final String PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME = "periodicalOperationType";
    public static final String SUBSCRIPTION_PERIOD_PARAM_NAME = "subscriptionPeriod";
    public static final String ENTITY_ID_PARAM_NAME = "entityId";
    public static final String USER_INVOICES_PARAM_NAME = "userInvoices";
    public static final String USER_SUBSCRIPTIONS_PARAM_NAME = "userSubscriptions";
    public static final String PERIODICAL_DESCRIPTION_PARAM_NAME = "periodicalDescription";
    public static final String PERIODICAL_STATUS_PARAM_NAME = "periodicalStatus";
    public static final String USER_ROLE_PARAM_NAME = "userRole";
    public static final String USER_EMAIL_PARAM_NAME = "userEmail";

    public static final String VALIDATION_BUNDLE_PATH = "webProject/i18n/validation";

    public static final String ADMIN_PANEL_VIEW_NAME = "adminPanel";
    public static final String PERIODICAL_LIST_VIEW_NAME = "periodicals/periodicalList";
    public static final String ONE_PERIODICAL_VIEW_NAME = "periodicals/onePeriodical";
    public static final String CREATE_EDIT_PERIODICAL_VIEW_NAME = "periodicals/createAndEdit";
    public static final String USER_LIST_VIEW_NAME = "users/userList";
    public static final String ONE_USER_INFO_VIEW_NAME = "users/userAccount";
    public static final String BACKEND_MAIN_PAGE_VIEW_NAME = "home";
    public static final String SIGN_UP_PAGE_VIEW_NAME = "signUp";

    public static final String ADMIN_PANEL_URI = "/backend/adminPanel";
    public static final String PERIODICAL_LIST_URI = "/backend/periodicals";
    public static final String LOGIN_PAGE = "/login.jsp";
    public static final String SIGN_IN_URI = "/backend/signIn";
    public static final String SIGN_OUT_URI = "/backend/signOut";
    public static final String SIGN_UP_URI = "/backend/signUp";
    public static final String ACCESS_DENIED_URI = "/accessDenied.jsp";
    public static final String USERS_LIST_URI = "/backend/users";
    public static final String CURRENT_USER_ACCOUNT_URI = "/backend/users/currentUser";
    public static final String PERIODICAL_CREATE_NEW_URI = "/backend/periodicals/createNew";
    public static final String PERIODICAL_DELETE_DISCARDED_URI = "/backend/periodicals/discarded";

    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_VALIDATION_FAILED = 412;

    public static final String PERIODICAL_NAME_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s!&?$#@'\"-]{2,45}";
    public static final String PERIODICAL_PUBLISHER_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s-]{2,45}";
    public static final String PERIODICAL_COST_PATTERN_REGEX = "0|[1-9]{1}\\d{0,8}";
    public static final String USER_EMAIL_PATTERN_REGEX =
            "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public static final String USER_PASSWORD_PATTERN_REGEX = "[\\w]{6,12}";

    public static final String MSG_KEY_CATEGORY_NEWS = "category.news";
    public static final String MSG_KEY_CATEGORY_NATURE = "category.nature";
    public static final String MSG_KEY_CATEGORY_FITNESS = "category.fitness";
    public static final String MSG_KEY_CATEGORY_BUSINESS = "category.business";
    public static final String MSG_KEY_CATEGORY_SPORTS = "category.sports";
    public static final String MSG_KEY_CATEGORY_SCIENCE_AND_ENGINEERING = "category.scienceAndEngineering";
    public static final String MSG_KEY_CATEGORY_TRAVELING = "category.traveling";

    public static final String MSG_SUCCESS = "validation.ok";
    public static final String MSG_CREDENTIALS_ARE_NOT_CORRECT = "validation.credentialsAreNotCorrect";
    public static final String USERNAME_IS_NOT_UNIQUE_TRY_ANOTHER_ONE = "validation.usernameIsNotUnique";
    public static final String USER_EMAIL_IS_NOT_UNIQUE_TRY_ANOTHER_ONE = "validation.userEmailIsNotUnique";
    public static final String MSG_PERIODICAL_NAME_INCORRECT = "periodicalName.validationError";
    public static final String MSG_PERIODICAL_NAME_DUPLICATION = "periodicalName.duplicationError";
    public static final String MSG_PERIODICAL_PUBLISHER_ERROR = "periodicalPublisher.validationError";
    public static final String MSG_PERIODICAL_CATEGORY_ERROR = "periodicalCategory.validationError";
    public static final String MSG_PERIODICAL_COST_ERROR = "periodicalCost.validationError";
    public static final String MSG_INCORRECT_USER_ID = "validation.invoiceOperation.incorrectUserId";
    public static final String MSG_VALIDATION_PASSED_SUCCESS = "validation.passedSuccessfully.success";
    public static final String MSG_VALIDATION_PERIODICAL_IS_NOT_VISIBLE =
            "validation.periodicalIsNotVisible";
    public static final String MSG_VALIDATION_NO_SUCH_INVOICE = "validation.invoice.noSuchInvoice";
    public static final String MSG_VALIDATION_INVOICE_IS_NOT_NEW = "validation.invoice.invoiceIsNotNew";
    public static final String MSG_INVOICE_PAYMENT_SUCCESS = "validation.invoiceWasPaid.success";
    public static final String MSG_INVOICE_PAYMENT_ERROR = "validation.invoice.payInvoiceError";
    public static final String MSG_VALIDATION_PERIODICAL_IS_NULL = "validation.periodicalIsNull";
    public static final String MSG_VALIDATION_SUBSCRIPTION_PERIOD_IS_NOT_VALID =
            "validation.subscriptionPeriodIsNotValid";
    public static final String MSG_INVOICE_CREATION_SUCCESS = "validation.invoiceCreated.success";
    public static final String MSG_INVOICE_PERSISTING_FAILED = "validation.invoicePersistingFailed";
    public static final String MSG_PERIODICALS_DELETED_SUCCESS =
            "validation.discardedPeriodicalsDeleted.success";
    public static final String MSG_NO_PERIODICALS_TO_DELETE =
            "validation.thereIsNoPeriodicalsToDelete.warning";
    public static final String INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL =
            "Incorrect periodicalOperationType during persisting a periodical.";
    public static final String MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_WARNING =
            "validation.periodicalHasActiveSubscriptions.warning";
    public static final String MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_ERROR =
            "validation.periodicalHasActiveSubscriptions.error";
    public static final String MSG_PERIODICAL_CREATED_SUCCESS = "periodicalCreatedNew.success";
    public static final String MSG_PERIODICAL_UPDATED_SUCCESS = "periodicalUpdated.success";
    public static final String MSG_PERIODICAL_PERSISTING_ERROR = "periodicalPersisting.error";
    public static final String MSG_ERROR_USER_IS_BLOCKED = "error.userIsBlocked";
    public static final String MSG_USER_EMAIL_REGEX_ERROR = "validation.userEmailIsIncorrect";
    public static final String MSG_USER_EMAIL_DUPLICATION_ERROR = "validation.userEmailIsNotUnique";
    public static final String MSG_USER_PASSWORD_ERROR = "validation.userPasswordIsIncorrect";
    public static final String MSG_NEW_USER_WAS_NOT_CREATED_ERROR = "userWasNotCreated.error";
    public static final String MSG_VALIDATION_PASSWORDS_ARE_NOT_EQUAL = "validation.passwordsAreNotEqual";

    public static final String METHODS_URI_SEPARATOR = ":";
    public static final String METHOD_METHOD_SEPARATOR = "\\|";
}
