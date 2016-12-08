package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.command.DisplayAdminPanelMainPage;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.command.invoice.PayOneInvoice;
import com.stolser.javatraining.webproject.controller.command.invoice.PersistOneInvoice;
import com.stolser.javatraining.webproject.controller.command.periodical.*;
import com.stolser.javatraining.webproject.controller.command.user.DisplayAllUsers;
import com.stolser.javatraining.webproject.controller.command.user.DisplayCurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class RequestProvider {
    private static final Map<String, RequestProcessor> requestMapping = new HashMap<>();

    static {
        requestMapping.put("GET:/adminPanel/?", new DisplayAdminPanelMainPage());
        requestMapping.put("GET:/adminPanel/users/?", new DisplayAllUsers());
        requestMapping.put("GET:/adminPanel/users/currentUser/?", new DisplayCurrentUser());
        requestMapping.put("GET:/adminPanel/periodicals/\\d+", new DisplayOnePeriodical());
        requestMapping.put("GET:/adminPanel/periodicals/?", new DisplayAllPeriodicals());
        requestMapping.put("POST:/adminPanel/periodicals/?", new PersistOnePeriodical());
        requestMapping.put("GET:/adminPanel/periodicals/createNew/?", new CreateNewPeriodical());
        requestMapping.put("GET:/adminPanel/periodicals/\\d+/update/?", new UpdatePeriodical());
        requestMapping.put("GET:/adminPanel/periodicals/discarded/delete/?", new DeleteDiscardedPeriodicals());
        requestMapping.put("POST:/adminPanel/users/\\d+/invoices/?", new PersistOneInvoice());
        requestMapping.put("POST:/adminPanel/users/\\d+/invoices/\\d+/pay/?", new PayOneInvoice());

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
