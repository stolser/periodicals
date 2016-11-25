package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.command.RequestCommand;
import com.stolser.javatraining.webproject.controller.command.periodical.DisplayOnePeriodical;
import com.stolser.javatraining.webproject.controller.command.user.DisplayAllUsers;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

public class RequestHelper {
    private static final Map<String, RequestCommand> requestMapping = new HashMap<>();

    static {
        requestMapping.put("GET:/admin/periodicals/\\d+", new DisplayOnePeriodical());
        requestMapping.put("GET:/admin/users", new DisplayAllUsers());

    }

    private HttpServletRequest request;

    public RequestHelper(HttpServletRequest request) {
        this.request = request;
    }

    public RequestCommand getCommand() throws NoSuchElementException {
        String method = request.getMethod().toUpperCase();
        String requestURI = request.getRequestURI();
        System.out.println("getCommand(): requestURI = '" + requestURI + "'");

        Optional<Map.Entry<String, RequestCommand>> mapping = requestMapping.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(method))
                .filter(entry -> {
                    String urlPattern = entry.getKey().split(":")[1];
                    System.out.println("urlPattern = '" + urlPattern + "'");

                    return Pattern.matches(urlPattern, requestURI);
                })
                .findFirst();

        System.out.println("mapping = " + mapping);

        return mapping.get().getValue();
    }
}
