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
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Provides mapping request uri to classes that will perform actual request processing.
 */
public final class RequestProviderImpl implements RequestProvider {
    public static final String GET_BACKEND_REQUEST_PATTERN = "GET:/backend/?";
    public static final String GET_ADMIN_PANEL_REQUEST_PATTERN = "GET:/backend/adminPanel/?";
    public static final String GET_ALL_USERS_REQUEST_PATTERN = "GET:/backend/users/?";
    public static final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:" + CURRENT_USER_ACCOUNT_URI + "/?";
    public static final String POST_SIGN_IN_REQUEST_PATTERN = "POST:" + SIGN_IN_URI + "/?";
    public static final String POST_PERSIST_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/?";
    public static final String POST_PAY_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/\\d+/pay/?";
    public static final String GET_ONE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+";
    public static final String GET_ALL_PERIODICALS_REQUEST_PATTERN = "GET:/backend/periodicals/?";
    public static final String POST_PERSIST_PERIODICAL_REQUEST_PATTERN = "POST:/backend/periodicals/?";
    public static final String GET_CREATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/createNew/?";
    public static final String GET_UPDATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+/update/?";
    public static final String POST_DELETE_PERIODICALS_REQUEST_PATTERN = "POST:/backend/periodicals/discarded/?";
    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:" + SIGN_OUT_URI + "/?";
    public static final String POST_SIGN_UP_REQUEST_PATTERN = "POST:" + SIGN_UP_URI + "/?";
    public static final String GET_SIGN_UP_REQUEST_PATTERN = "GET:" + SIGN_UP_URI + "/?";
    public static final String POST_AJAX_FORM_VALIDATOR_REQUEST_PATTERN = "POST:/backend/validation";

    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();
    private static final String NO_MAPPING_FOR_SUCH_REQUEST = "There no mapping for such a request: '%s'.";

    static {
        requestMapping.put(POST_SIGN_IN_REQUEST_PATTERN, SignIn.getInstance());
        requestMapping.put(GET_BACKEND_REQUEST_PATTERN, BackendHomePage.getInstance());
        requestMapping.put(GET_ADMIN_PANEL_REQUEST_PATTERN, DisplayAdminPanel.getInstance());
        requestMapping.put(GET_ALL_USERS_REQUEST_PATTERN, DisplayAllUsers.getInstance());
        requestMapping.put(GET_CURRENT_USER_REQUEST_PATTERN, DisplayCurrentUser.getInstance());
        requestMapping.put(POST_PERSIST_INVOICE_REQUEST_PATTERN, PersistOneInvoice.getInstance());
        requestMapping.put(POST_PAY_INVOICE_REQUEST_PATTERN, PayOneInvoice.getInstance());
        requestMapping.put(GET_ONE_PERIODICAL_REQUEST_PATTERN, DisplayOnePeriodical.getInstance());
        requestMapping.put(GET_ALL_PERIODICALS_REQUEST_PATTERN, DisplayAllPeriodicals.getInstance());
        requestMapping.put(POST_PERSIST_PERIODICAL_REQUEST_PATTERN, PersistOnePeriodical.getInstance());
        requestMapping.put(GET_CREATE_PERIODICAL_REQUEST_PATTERN, CreateNewPeriodical.getInstance());
        requestMapping.put(GET_UPDATE_PERIODICAL_REQUEST_PATTERN, UpdatePeriodical.getInstance());
        requestMapping.put(POST_DELETE_PERIODICALS_REQUEST_PATTERN, DeleteDiscardedPeriodicals.getInstance());
        requestMapping.put(GET_SIGN_OUT_REQUEST_PATTERN, SignOut.getInstance());
        requestMapping.put(POST_SIGN_UP_REQUEST_PATTERN, CreateUser.getInstance());
        requestMapping.put(GET_SIGN_UP_REQUEST_PATTERN, DisplaySignUpPage.getInstance());
        requestMapping.put(POST_AJAX_FORM_VALIDATOR_REQUEST_PATTERN, AjaxFormValidation.getInstance());
    }

    private RequestProviderImpl() {
    }

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
        String requestUri = request.getRequestURI();
        Predicate<Map.Entry<String, RequestProcessor>> mappingContainsRequestMethod = entry -> {
            String methodPattern = entry.getKey().split(":")[0];
            String[] methods = methodPattern.split("\\|");
            String requestMethod = request.getMethod().toUpperCase();

            return Arrays.asList(methods).contains(requestMethod);
        };

        Predicate<Map.Entry<String, RequestProcessor>> mappingMatchesRequestUri = entry -> {
            String urlPattern = entry.getKey().split(":")[1];
            return Pattern.matches(urlPattern, requestUri);
        };

        Optional<Map.Entry<String, RequestProcessor>> currentMapping = requestMapping.entrySet()
                .stream()
                .filter(mappingContainsRequestMethod)
                .filter(mappingMatchesRequestUri)
                .findFirst();

        if (currentMapping.isPresent()) {
            return currentMapping.get().getValue();
        } else {
            throw new NoSuchElementException(
                    String.format(NO_MAPPING_FOR_SUCH_REQUEST, requestUri));
        }
    }
}
