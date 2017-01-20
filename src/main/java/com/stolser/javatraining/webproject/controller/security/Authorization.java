package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProviderImpl;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ADMIN_ROLE_NAME;

/**
 * Encapsulates information about resource access permissions of each type of roles.
 */
final class Authorization {
    private static final Map<String, Set<String>> permissionMapping = new HashMap<>();

    static {
        Set<String> admin = new HashSet<>(Collections.singletonList(ADMIN_ROLE_NAME));

        permissionMapping.put(RequestProviderImpl.GET_ALL_USERS_REQUEST_PATTERN, admin);
        permissionMapping.put(RequestProviderImpl.GET_CREATE_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(RequestProviderImpl.GET_UPDATE_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(RequestProviderImpl.POST_PERSIST_PERIODICAL_REQUEST_PATTERN, admin);
        permissionMapping.put(RequestProviderImpl.POST_DELETE_PERIODICALS_REQUEST_PATTERN, admin);
        permissionMapping.put(RequestProviderImpl.GET_ADMIN_PANEL_REQUEST_PATTERN, admin);
    }

    private Authorization() {}

    private static class InstanceHolder {
        private static final Authorization INSTANCE = new Authorization();
    }

    static Authorization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Checks whether a current user has enough permissions to access a requested uri
     * using a current http method.
     *
     * @param request a current http request
     * @return {@code true} - if a current user has enough permissions to perform such a kind of requests,
     *      and {@code false} otherwise
     */
    boolean checkPermissions(HttpServletRequest request) {
        String requestMethod = request.getMethod().toUpperCase();
        String requestUri = request.getRequestURI();

        Optional<Map.Entry<String, Set<String>>> thisPermissionMapping =
                permissionMapping.entrySet()
                        .stream()
                        .filter(entry -> {
                            String[] methods = extractHttpMethodsFromMapping(entry);
                            return Arrays.asList(methods).contains(requestMethod);
                        })
                        .filter(entry -> {
                            String urlPattern = entry.getKey().split(":")[1];
                            return Pattern.matches(urlPattern, requestUri);
                        })
                        .findFirst();

        return isPermissionGranted(thisPermissionMapping, request);
    }

    private String[] extractHttpMethodsFromMapping(Map.Entry<String, Set<String>> entry) {
        String methodPattern = entry.getKey().split(":")[0];
        return methodPattern.split("\\|");
    }

    private boolean hasUserLegitRole(Set<String> userRoles, Set<String> legitRoles) {
        Set<String> userLegitRoles = new HashSet<>(legitRoles);
        userLegitRoles.retainAll(userRoles);

        return !userLegitRoles.isEmpty();
    }

    private boolean isPermissionGranted(Optional<Map.Entry<String, Set<String>>> thisPermissionMapping,
                                        HttpServletRequest request) {
        boolean permissionGranted = true;
        if (thisPermissionMapping.isPresent()) {
            User user = (User) request.getSession().getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
            Set<String> userRoles = user.getRoles();
            Set<String> legitRoles = thisPermissionMapping.get().getValue();

            permissionGranted = hasUserLegitRole(userRoles, legitRoles);
        }

        return permissionGranted;
    }
}
