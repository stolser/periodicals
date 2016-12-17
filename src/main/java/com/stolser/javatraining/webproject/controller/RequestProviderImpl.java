package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request_processor.BackendMainPage;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.request_processor.SignOut;
import com.stolser.javatraining.webproject.controller.request_processor.admin.DisplayAdminPanel;
import com.stolser.javatraining.webproject.controller.request_processor.invoice.PayOneInvoice;
import com.stolser.javatraining.webproject.controller.request_processor.invoice.PersistOneInvoice;
import com.stolser.javatraining.webproject.controller.request_processor.periodical.*;
import com.stolser.javatraining.webproject.controller.request_processor.user.DisplayAllUsers;
import com.stolser.javatraining.webproject.controller.request_processor.user.DisplayCurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

final class RequestProviderImpl implements RequestProvider {
    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();
    private static final String NO_MAPPING_FOR_SUCH_REQUEST = "There no mapping for such a request: '%s'.";

    static {
        requestMapping.put(GET_BACKEND_REQUEST_PATTERN, new BackendMainPage());
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
    }

    private RequestProviderImpl() {
    }

    private static class InstanceHolder {
        private static final RequestProviderImpl INSTANCE = new RequestProviderImpl();
    }

    public static RequestProviderImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public RequestProcessor getRequestProcessor(HttpServletRequest request) {
        String requestMethod = request.getMethod().toUpperCase();
        String requestURI = request.getRequestURI();

        System.out.println("requestMethod = " + requestMethod);
        System.out.println("getRequestProcessor(): requestURI = '" + requestURI + "'");

        Optional<Map.Entry<String, RequestProcessor>> mapping = requestMapping.entrySet()
                .stream()
                .filter(entry -> {
                    String methodPattern = entry.getKey().split(":")[0];
                    String[] methods = methodPattern.split("\\|");  // is necessary for
                    // the "GET|POST|PUT|DELETE" notation;

                    return Arrays.asList(methods).contains(requestMethod);
                })
                .filter(entry -> {  // filtering by a Uri pattern;
                    String urlPattern = entry.getKey().split(":")[1];
//                    System.out.println("urlPattern = '" + urlPattern + "'");

                    return Pattern.matches(urlPattern, requestURI);
                })
                .findFirst();

        System.out.println("mapping = " + mapping);

        if (mapping.isPresent()) {
            return mapping.get().getValue();
        } else {
            throw new NoSuchElementException(
                    String.format(NO_MAPPING_FOR_SUCH_REQUEST, requestURI));
        }
    }
}
