package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.request_processor.BackendMainPage;
import com.stolser.javatraining.webproject.controller.request_processor.SignOut;
import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.request_processor.invoice.PayOneInvoice;
import com.stolser.javatraining.webproject.controller.request_processor.invoice.PersistOneInvoice;
import com.stolser.javatraining.webproject.controller.request_processor.periodical.*;
import com.stolser.javatraining.webproject.controller.request_processor.user.DisplayAllUsers;
import com.stolser.javatraining.webproject.controller.request_processor.user.DisplayCurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class RequestProvider {
    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();

    static {
        requestMapping.put("GET:/backend/?", new BackendMainPage());
        requestMapping.put("GET:/backend/users/?", new DisplayAllUsers());
        requestMapping.put("GET:/backend/users/currentUser/?", new DisplayCurrentUser());
        requestMapping.put("GET:/backend/periodicals/\\d+", new DisplayOnePeriodical());
        requestMapping.put("GET:/backend/periodicals/?", new DisplayAllPeriodicals());
        requestMapping.put("POST:/backend/periodicals/?", new PersistOnePeriodical());
        requestMapping.put("GET:/backend/periodicals/createNew/?", new CreateNewPeriodical());
        requestMapping.put("GET:/backend/periodicals/\\d+/update/?", new UpdatePeriodical());
        requestMapping.put("GET:/backend/periodicals/discarded/delete/?", new DeleteDiscardedPeriodicals());
        requestMapping.put("POST:/backend/users/\\d+/invoices/?", new PersistOneInvoice());
        requestMapping.put("POST:/backend/users/\\d+/invoices/\\d+/pay/?", new PayOneInvoice());
        requestMapping.put("GET:/backend/signOut/?", new SignOut());

    }

    private HttpServletRequest request;

    public RequestProvider(HttpServletRequest request) {
        this.request = request;
    }

    public RequestProcessor getRequestProcessor() throws NoSuchElementException {
        String requestMethod = request.getMethod().toUpperCase();
        String requestURI = request.getRequestURI();
        System.out.println("getRequestProcessor(): requestURI = '" + requestURI + "'");

        Optional<Map.Entry<String, RequestProcessor>> mapping = requestMapping.entrySet()
                .stream()
                .filter(entry -> {
                    String methodPattern = entry.getKey().split(":")[0];
                    String[] methods = methodPattern.split("\\|");

                    return Arrays.asList(methods).contains(requestMethod);
                })
                .filter(entry -> {
                    String urlPattern = entry.getKey().split(":")[1];
                    System.out.println("urlPattern = '" + urlPattern + "'");

                    return Pattern.matches(urlPattern, requestURI);
                })
                .findFirst();

        System.out.println("mapping = " + mapping);

        if (mapping.isPresent()) {
            return getRequestProcessorInstance(mapping.get().getValue());
        } else {
            throw new NoSuchElementException(
                    String.format("There no mapping for such a request: '%s'.", requestURI));
        }
    }

    /**
     * If a request processor can be cached (a class is small and does not have instance fields),
     * then this method returns a cached instance. Otherwise it returns a new instance.
     * If any RequestProcessor class becomes too large and needs to be refactored
     * and as a result of refactoring it acquires some fields, another if-block must be added here.
     */
    private RequestProcessor getRequestProcessorInstance(RequestProcessor cachedProcessor) {

        if (cachedProcessor instanceof PersistOneInvoice) {
            return new PersistOneInvoice();
        }

        if (cachedProcessor instanceof PayOneInvoice) {
            return new PayOneInvoice();
        }

        return cachedProcessor;
    }
}
