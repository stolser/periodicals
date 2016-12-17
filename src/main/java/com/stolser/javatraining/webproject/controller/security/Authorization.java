package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

class Authorization {
    private static final Map<String, Set<String>> permissionMapping = new HashMap<>();

    static {
        Set<String> admin = new HashSet<>(Collections.singletonList(ADMIN_ROLE_NAME));

        permissionMapping.put(GET_ALL_USERS_REQUEST_PATTERN, admin);
        permissionMapping.put(GET_CREATE_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(GET_UPDATE_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(POST_PERSIST_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(POST_DELETE_PERIODICALS_REQUEST_PATTERN, admin);
        permissionMapping.put(GET_ADMIN_PANEL_REQUEST_PATTERN, admin);
    }

    private Authorization() {
    }

    private static class InstanceHolder {
        private static final Authorization INSTANCE = new Authorization();
    }

    static Authorization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    boolean checkPermissions(HttpServletRequest request) {
        String requestMethod = request.getMethod().toUpperCase();
        String requestURI = request.getRequestURI();

        Optional<Map.Entry<String, Set<String>>> thisPermissionMapping = permissionMapping.entrySet()
                .stream()
                .filter(entry -> {  // filtering by a method;
                    String methodPattern = entry.getKey().split(":")[0];
                    String[] methods = methodPattern.split("\\|");  // is necessary for
                    // the "GET|POST|PUT|DELETE" notation;

                    return Arrays.asList(methods).contains(requestMethod);
                })
                .filter(entry -> {  // filtering by a Uri pattern;
                    String urlPattern = entry.getKey().split(":")[1];
                    return Pattern.matches(urlPattern, requestURI);
                })
                .findFirst();

        boolean permissionGranted = true;
        if (thisPermissionMapping.isPresent()) {
            User user = (User) request.getSession().getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
            Set<String> userRoles = user.getRoles();
            Set<String> legitRoles = thisPermissionMapping.get().getValue();

            permissionGranted = userHasLegitRole(userRoles, legitRoles);
        }

        return permissionGranted;
    }

    private boolean userHasLegitRole(Set<String> userRoles, Set<String> legitRoles) {
        Set<String> userLegitRoles = new HashSet<>(legitRoles);
        userLegitRoles.retainAll(userRoles);

        return (userLegitRoles.size() != 0);
    }
}