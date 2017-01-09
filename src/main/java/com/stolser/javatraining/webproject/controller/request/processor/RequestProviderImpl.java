package com.stolser.javatraining.webproject.controller.request.processor;

import com.stolser.javatraining.webproject.controller.form.validator.AjaxFormValidation;
import com.stolser.javatraining.webproject.controller.request.processor.invoice.PersistOneInvoice;
import com.stolser.javatraining.webproject.controller.request.processor.periodical.*;
import com.stolser.javatraining.webproject.controller.request.processor.user.CreateUser;
import com.stolser.javatraining.webproject.controller.request.processor.user.DisplayAllUsers;
import com.stolser.javatraining.webproject.controller.request.processor.admin.panel.DisplayAdminPanel;
import com.stolser.javatraining.webproject.controller.request.processor.invoice.PayOneInvoice;
import com.stolser.javatraining.webproject.controller.request.processor.user.DisplayCurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Provides mapping request uri to classes that will perform actual request processing.
 */
public final class RequestProviderImpl implements RequestProvider {
    public static final String GET_BACKEND_REQUEST_PATTERN = "GET:/backend/?";
    public static final String GET_ADMIN_PANEL_REQUEST_PATTERN = "GET:/backend/adminPanel/?";
    public static final String GET_ALL_USERS_REQUEST_PATTERN = "GET:/backend/users/?";
    public static final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:/backend/users/currentUser/?";
    public static final String POST_SIGN_IN_REQUEST_PATTERN = "POST:/backend/signIn/?";
    public static final String POST_PERSIST_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/?";
    public static final String POST_PAY_INVOICE_REQUEST_PATTERN = "POST:/backend/users/\\d+/invoices/\\d+/pay/?";
    public static final String GET_ONE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+";
    public static final String GET_ALL_PERIODICALS_REQUEST_PATTERN = "GET:/backend/periodicals/?";
    public static final String POST_PERSIST_PERIODICAL_REQUEST_PATTERN = "POST:/backend/periodicals/?";
    public static final String GET_CREATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/createNew/?";
    public static final String GET_UPDATE_PERIODICAL_REQUEST_PATTERN = "GET:/backend/periodicals/\\d+/update/?";
    public static final String POST_DELETE_PERIODICALS_REQUEST_PATTERN = "POST:/backend/periodicals/discarded/?";
    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:/backend/signOut/?";
    public static final String POST_SIGN_UP = "POST:/backend/signUp";
    public static final String POST_AJAX_FORM_VALIDATOR = "POST:/backend/validation";

    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();
    private static final String NO_MAPPING_FOR_SUCH_REQUEST = "There no mapping for such a request: '%s'.";

    static {
        requestMapping.put(POST_SIGN_IN_REQUEST_PATTERN, new SignIn());
        requestMapping.put(GET_BACKEND_REQUEST_PATTERN, new BackendHomePage());
        requestMapping.put(GET_ADMIN_PANEL_REQUEST_PATTERN, new DisplayAdminPanel());
        requestMapping.put(GET_ALL_USERS_REQUEST_PATTERN, new DisplayAllUsers());
        requestMapping.put(GET_CURRENT_USER_REQUEST_PATTERN, new DisplayCurrentUser());
        requestMapping.put(POST_PERSIST_INVOICE_REQUEST_PATTERN, new PersistOneInvoice());
        requestMapping.put(POST_PAY_INVOICE_REQUEST_PATTERN, new PayOneInvoice());
        requestMapping.put(GET_ONE_PERIODICAL_REQUEST_PATTERN, new DisplayOnePeriodical());
        requestMapping.put(GET_ALL_PERIODICALS_REQUEST_PATTERN, new DisplayAllPeriodicals());
        requestMapping.put(POST_PERSIST_PERIODICAL_REQUEST_PATTERN, new PersistOnePeriodical());
        requestMapping.put(GET_CREATE_PERIODICAL_REQUEST_PATTERN, new CreateNewPeriodical());
        requestMapping.put(GET_UPDATE_PERIODICAL_REQUEST_PATTERN, new UpdatePeriodical());
        requestMapping.put(POST_DELETE_PERIODICALS_REQUEST_PATTERN, new DeleteDiscardedPeriodicals());
        requestMapping.put(GET_SIGN_OUT_REQUEST_PATTERN, new SignOut());
        requestMapping.put(POST_SIGN_UP, new CreateUser());
        requestMapping.put(POST_AJAX_FORM_VALIDATOR, new AjaxFormValidation());
    }

    private RequestProviderImpl() {}

    private static class InstanceHolder {
        private static final RequestProviderImpl INSTANCE = new RequestProviderImpl();
    }

    public static RequestProviderImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * @param request a current http request
     * @return a specific implementation of the {@RequestProcessor} interface that processes
     * http requests to this request uri
     */
    @Override
    public RequestProcessor getRequestProcessor(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Predicate<Map.Entry<String, RequestProcessor>> mappingContainsRequestMethod = entry -> {
            String methodPattern = entry.getKey().split(":")[0];
            String[] methods = methodPattern.split("\\|");
            String requestMethod = request.getMethod().toUpperCase();

            return Arrays.asList(methods).contains(requestMethod);
        };

        Predicate<Map.Entry<String, RequestProcessor>> mappingMatchesRequestUri = entry -> {
            String urlPattern = entry.getKey().split(":")[1];
            return Pattern.matches(urlPattern, requestURI);
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
                    String.format(NO_MAPPING_FOR_SUCH_REQUEST, requestURI));
        }
    }
}
