package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.model.storage.StorageException;

import java.util.Locale;
import java.util.NoSuchElementException;

public class ApplicationResources {
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String STATUS_CODE_JSON_RESPONSE = "statusCode";
    public static final String VALIDATION_MESSAGE_JSON_RESPONSE = "validationMessage";
    public static final String ALGORITHM_NAME = "MD5";

    public static final String JSP_PRIVATE_RESOURCE_PATH_PATTERN = "/WEB-INF/backend/%s.jsp";
    public static final String JSP_PUBLIC_RESOURCE_PATH_PATTERN = "/%s.jsp";

    public static final String GET_BACKEND_REQUEST_PATTERN = "GET:/backend/?";
    public static final String GET_ADMIN_PANEL_REQUEST_PATTERN = "GET:/backend/adminPanel/?";
    public static final String GET_ALL_USERS_REQUEST_PATTERN = "GET:/backend/users/?";
    public static final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:/backend/users/currentUser/?";
    public static final String POST_PERSIST_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/?";
    public static final String POST_PAY_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/\\d+/pay/?";
    public static final String GET_ONE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+";
    public static final String GET_ALL_PERIODICALS_REQUEST_PATTERN = "GET:/backend/periodicals/?";
    public static final String POST_PERSIST_PERIODICAL_REQUEST_PATTERN = "POST:/backend/periodicals/?";
    public static final String GET_CREATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/createNew/?";
    public static final String GET_UPDATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+/update/?";
    public static final String POST_DELETE_PERIODICALS_REQUEST_PATTERN = "POST:/backend/periodicals/discarded/?";
    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:/backend/signOut/?";

    public static final String MESSAGES_ATTR_NAME = "messages";
    public static final String GENERAL_MESSAGES_FRONT_BLOCK_NAME = "topMessages";
    public static final String CURRENT_USER_ATTR_NAME = "thisUser";
    public static final String PERIODICAL_ATTR_NAME = "periodical";
    public static final String PERIODICAL_STATUSES_ATTR_NAME = "periodicalStatuses";
    public static final String PERIODICAL_CATEGORIES_ATTR_NAME = "periodicalCategories";
    public static final String ORIGINAL_URI_ATTR_NAME = "originalUri";
    public static final String USERNAME_ATTR_NAME = "username";
    public static final String ALL_PERIODICALS_ATTR_NAME = "allPeriodicals";
    public static final String ALL_USERS_ATTR_NAME = "allUsers";
    public static final String LANGUAGE_ATTR_NAME = "language";
    public static final String PERIODICAL_STATISTICS_ATTR_NAME = "periodicalStatistics";
    public static final String FINANCIAL_STATISTICS_ATTR_NAME = "financialStatistics";

    public static final String PARAM_NAME = "paramName";
    public static final String PARAM_VALUE = "paramValue";
    public static final String SIGN_IN_USERNAME_PARAM_NAME = "signInUsername";
    public static final String PASSWORD_PARAM_NAME = "password";
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

    public static final String VALIDATION_BUNDLE_PATH = "webProject/i18n/validation";

    public static final String ADMIN_PANEL_VIEW_NAME = "adminPanel";
    public static final String PERIODICAL_LIST_VIEW_NAME = "periodicals/periodicalList";
    public static final String ONE_PERIODICAL_VIEW_NAME = "periodicals/onePeriodical";
    public static final String CREATE_EDIT_PERIODICAL_VIEW_NAME = "periodicals/createAndEdit";
    public static final String USER_LIST_VIEW_NAME = "users/userList";
    public static final String ONE_USER_INFO_VIEW_NAME = "users/userAccount";
    public static final String BACKEND_MAIN_PAGE_VIEW_NAME = "home";

    public static final String ADMIN_PANEL_URI = "/backend/adminPanel";
    public static final String PERIODICAL_LIST_URI = "/backend/periodicals";
    public static final String SIGN_IN_URI = "/login.jsp";
    public static final String SIGN_OUT_URI = "/backend/signOut";
    public static final String ACCESS_DENIED_URI = "/accessDenied.jsp";
    public static final String CURRENT_USER_ACCOUNT_URI = "/backend/users/currentUser";
    public static final String PERIODICAL_CREATE_NEW_URI = "/backend/periodicals/createNew";
    public static final String PERIODICAL_DELETE_DISCARDED_URI = "/backend/periodicals/discarded";
    public static final String PERIODICAL_CREATE_UPDATE_URI = "/backend/periodicals";

    public static final String ADMIN_ROLE_NAME = "admin";

    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_VALIDATION_FAILED = 412;

    public static final String PERIODICAL_NAME_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s!&?$#@'\"-]{2,45}";
    public static final String PERIODICAL_PUBLISHER_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s-]{2,45}";
    public static final String PERIODICAL_COST_PATTERN_REGEX = "0|[1-9]{1}\\d{0,8}";

    private static final String PAGE_404_VIEW_NAME = "errors/page-404";
    private static final String STORAGE_EXCEPTION_PAGE_VIEW_NAME = "errors/storage-error-page";
    private static final String GENERAL_ERROR_PAGE_VIEW_NAME = "errors/error-page";

    public static final String MSG_KEY_CATEGORY_NEWS = "category.news";
    public static final String MSG_KEY_CATEGORY_NATURE = "category.nature";
    public static final String MSG_KEY_CATEGORY_FITNESS = "category.fitness";
    public static final String MSG_KEY_CATEGORY_BUSINESS = "category.business";
    public static final String MSG_KEY_CATEGORY_SPORTS = "category.sports";
    public static final String MSG_KEY_CATEGORY_SCIENCE_AND_ENGINEERING = "category.scienceAndEngineering";
    public static final String MSG_KEY_CATEGORY_TRAVELING = "category.traveling";

    public static final String MSG_SUCCESS = "validation.ok";
    public static final String MSG_NO_SUCH_USER_NAME = "validation.noSuchUserName";
    public static final String MSG_USER_IS_BLOCKED = "validation.userIsBlocked";
    public static final String MSG_PERIODICAL_NAME_ERROR = "periodicalName.validationError";
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
    public static final String MSG_ERROR_WRONG_PASSWORD = "error.wrongPassword";

    public static final String DB_CONFIG_FILENAME = "webProject/config/dbConfig.properties";
    public static final String DB_CONFIG_PARAM_URL = "database.url";
    public static final String DB_CONFIG_PARAM_DB_NAME = "database.dbName";
    public static final String DB_CONFIG_PARAM_USER_NAME = "database.userName";
    public static final String DB_CONFIG_PARAM_USER_PASSWORD = "database.userPassword";
    public static final String DB_CONFIG_PARAM_MAX_CONN_NUMBER = "database.maxConnNumber";

    public static final String DB_CREDENTIALS_ID = "credentials.id";
    public static final String DB_CREDENTIALS_USER_NAME = "credentials.user_name";
    public static final String DB_CREDENTIALS_PASSWORD_HASH = "credentials.password_hash";
    public static final String DB_INVOICES_ID = "invoices.id";
    public static final String DB_INVOICES_USER_ID = "invoices.user_id";
    public static final String DB_INVOICES_PERIODICAL_ID = "invoices.periodical_id";
    public static final String DB_INVOICES_PERIOD = "invoices.period";
    public static final String DB_INVOICES_TOTAL_SUM = "invoices.total_sum";
    public static final String DB_INVOICES_STATUS = "invoices.status";
    public static final String DB_INVOICES_CREATION_DATE = "invoices.creation_date";
    public static final String DB_INVOICES_PAYMENT_DATE = "invoices.payment_date";
    public static final String DB_PERIODICALS_ID = "periodicals.id";
    public static final String DB_PERIODICALS_NAME = "periodicals.name";
    public static final String DB_PERIODICALS_CATEGORY = "periodicals.category";
    public static final String DB_PERIODICALS_PUBLISHER = "periodicals.publisher";
    public static final String DB_PERIODICALS_DESCRIPTION = "periodicals.description";
    public static final String DB_PERIODICALS_ONE_MONTH_COST = "periodicals.one_month_cost";
    public static final String DB_PERIODICALS_STATUS = "periodicals.status";
    public static final String DB_USER_ROLES_NAME = "user_roles.name";
    public static final String DB_SUBSCRIPTIONS_ID = "subscriptions.id";
    public static final String DB_SUBSCRIPTIONS_USER_ID = "subscriptions.user_id";
    public static final String DB_SUBSCRIPTIONS_PERIODICAL_ID = "subscriptions.periodical_id";
    public static final String DB_SUBSCRIPTIONS_DELIVERY_ADDRESS = "subscriptions.delivery_address";
    public static final String DB_SUBSCRIPTIONS_END_DATE = "subscriptions.end_date";
    public static final String DB_SUBSCRIPTIONS_STATUS = "subscriptions.status";
    public static final String DB_USERS_ID = "users.id";
    public static final String DB_USERS_FIRST_NAME = "users.first_name";
    public static final String DB_USERS_LAST_NAME = "users.last_name";
    public static final String DB_USERS_BIRTHDAY = "users.birthday";
    public static final String DB_USERS_EMAIL = "users.email";
    public static final String DB_USERS_ADDRESS = "users.address";
    public static final String DB_USERS_STATUS = "users.status";

    public static String getErrorViewName(Throwable exception) {
        String viewName = GENERAL_ERROR_PAGE_VIEW_NAME;

        if (exception instanceof StorageException) {
            viewName = STORAGE_EXCEPTION_PAGE_VIEW_NAME;
        }

        if (exception instanceof NoSuchElementException) {
            viewName = PAGE_404_VIEW_NAME;
        }

        return viewName;
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
