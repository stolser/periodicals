package com.stolser.javatraining.webproject.controller.request.processor;

import com.stolser.javatraining.webproject.controller.form.validator.AjaxFormValidation;
import com.stolser.javatraining.webproject.controller.request.processor.admin.panel.DisplayAdminPanel;
import com.stolser.javatraining.webproject.controller.request.processor.invoice.PayOneInvoice;
import com.stolser.javatraining.webproject.controller.request.processor.invoice.PersistOneInvoice;
import com.stolser.javatraining.webproject.controller.request.processor.periodical.*;
import com.stolser.javatraining.webproject.controller.request.processor.sign.DisplaySignUpPage;
import com.stolser.javatraining.webproject.controller.request.processor.sign.SignIn;
import com.stolser.javatraining.webproject.controller.request.processor.sign.SignOut;
import com.stolser.javatraining.webproject.controller.request.processor.user.CreateUser;
import com.stolser.javatraining.webproject.controller.request.processor.user.DisplayAllUsers;
import com.stolser.javatraining.webproject.controller.request.processor.user.DisplayCurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static com.stolser.javatraining.webproject.controller.utils.HttpUtils.filterRequestByHttpMethod;
import static com.stolser.javatraining.webproject.controller.utils.HttpUtils.filterRequestByUri;

/**
 * Provides mapping request uri to classes that will perform actual request processing.
 */
public final class RequestProviderImpl implements RequestProvider {
    public static final String GET_BACKEND_REQUEST_PATTERN = "GET:/backend/?";
    public static final String GET_ADMIN_PANEL_REQUEST_PATTERN = "GET:" + ADMIN_PANEL_URI + "/?";
    public static final String GET_ALL_USERS_REQUEST_PATTERN = "GET:" + USERS_LIST_URI + "/?";
    public static final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:" + CURRENT_USER_ACCOUNT_URI + "/?";
    public static final String POST_SIGN_IN_REQUEST_PATTERN = "POST:" + SIGN_IN_URI + "/?";
    public static final String POST_PERSIST_INVOICE_REQUEST_PATTERN =
            "POST:" + USERS_LIST_URI + "/\\d+/invoices/?";
    public static final String POST_PAY_INVOICE_REQUEST_PATTERN =
            "POST:" + USERS_LIST_URI + "/\\d+/invoices/\\d+/pay/?";
    public static final String GET_ONE_PERIODICAL_REQUEST_PATTERN = "GET:" + PERIODICAL_LIST_URI + "/\\d+";
    public static final String GET_ALL_PERIODICALS_REQUEST_PATTERN = "GET:" + PERIODICAL_LIST_URI + "/?";
    public static final String POST_PERSIST_PERIODICAL_REQUEST_PATTERN = "POST:" + PERIODICAL_LIST_URI + "/?";
    public static final String GET_CREATE_PERIODICAL_REQUEST_PATTERN = "GET:" + PERIODICAL_LIST_URI + "/createNew/?";
    public static final String GET_UPDATE_PERIODICAL_REQUEST_PATTERN =
            "GET:" + PERIODICAL_LIST_URI + "/\\d+/update/?";
    public static final String POST_DELETE_PERIODICALS_REQUEST_PATTERN =
            "POST:" + PERIODICAL_LIST_URI + "/discarded/?";
    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:" + SIGN_OUT_URI + "/?";
    public static final String POST_SIGN_UP_REQUEST_PATTERN = "POST:" + SIGN_UP_URI + "/?";
    public static final String GET_SIGN_UP_REQUEST_PATTERN = "GET:" + SIGN_UP_URI + "/?";
    public static final String POST_AJAX_FORM_VALIDATOR_REQUEST_PATTERN = "POST:/backend/validation";

    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();
    private static final String NO_MAPPING_FOR_SUCH_REQUEST = "There no mapping for such a request: '%s'.";

    static {
        requestMapping.put(POST_SIGN_IN_REQUEST_PATTERN, SignIn.getInstance());
        requestMapping.put(GET_BACKEND_REQUEST_PATTERN, DisplayBackendHomePage.getInstance());
        requestMapping.put(GET_ADMIN_PANEL_REQUEST_PATTERN, DisplayAdminPanel.getInstance());
        requestMapping.put(GET_ALL_USERS_REQUEST_PATTERN, DisplayAllUsers.getInstance());
        requestMapping.put(GET_CURRENT_USER_REQUEST_PATTERN, DisplayCurrentUser.getInstance());
        requestMapping.put(POST_PERSIST_INVOICE_REQUEST_PATTERN, PersistOneInvoice.getInstance());
        requestMapping.put(POST_PAY_INVOICE_REQUEST_PATTERN, PayOneInvoice.getInstance());
        requestMapping.put(GET_ONE_PERIODICAL_REQUEST_PATTERN, DisplayOnePeriodical.getInstance());
        requestMapping.put(GET_ALL_PERIODICALS_REQUEST_PATTERN, DisplayAllPeriodicals.getInstance());
        requestMapping.put(POST_PERSIST_PERIODICAL_REQUEST_PATTERN, PersistOnePeriodical.getInstance());
        requestMapping.put(GET_CREATE_PERIODICAL_REQUEST_PATTERN, DisplayNewPeriodicalPage.getInstance());
        requestMapping.put(GET_UPDATE_PERIODICAL_REQUEST_PATTERN, DisplayUpdatePeriodicalPage.getInstance());
        requestMapping.put(POST_DELETE_PERIODICALS_REQUEST_PATTERN, DeleteDiscardedPeriodicals.getInstance());
        requestMapping.put(GET_SIGN_OUT_REQUEST_PATTERN, SignOut.getInstance());
        requestMapping.put(POST_SIGN_UP_REQUEST_PATTERN, CreateUser.getInstance());
        requestMapping.put(GET_SIGN_UP_REQUEST_PATTERN, DisplaySignUpPage.getInstance());
        requestMapping.put(POST_AJAX_FORM_VALIDATOR_REQUEST_PATTERN, AjaxFormValidation.getInstance());
    }

    private RequestProviderImpl() {}

    private static class InstanceHolder {
        private static final RequestProviderImpl INSTANCE = new RequestProviderImpl();
    }

    public static RequestProviderImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Determines a specific request processor to process the request depending on the current
     * http method and uri.
     * @param request a current http request
     * @return a specific implementation of the {@link RequestProcessor} interface that processes
     *      http requests to this request uri
     */
    @Override
    public RequestProcessor getRequestProcessor(HttpServletRequest request) {
        Optional<Map.Entry<String, RequestProcessor>> currentMapping =
                requestMapping.entrySet()
                .stream()
                .filter(entry -> filterRequestByHttpMethod(request, entry.getKey()))
                .filter(entry -> filterRequestByUri(request, entry.getKey()))
                .findFirst();

        if (currentMapping.isPresent()) {
            return currentMapping.get().getValue();
        }

        throw new NoSuchElementException(
                String.format(NO_MAPPING_FOR_SUCH_REQUEST, request.getRequestURI()));
    }
}
