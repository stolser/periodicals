package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProviderImpl;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Encapsulates information about resource access permissions of each type of roles.
 */
final class Authorization {
    private static final Map<String, Set<User.Role>> permissionMapping = new HashMap<>();

    static {
        Set<User.Role> admin = new HashSet<>(Collections.singletonList(User.Role.ADMIN));

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

        Optional<Map.Entry<String, Set<User.Role>>> thisPermissionMapping =
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

    private String[] extractHttpMethodsFromMapping(Map.Entry<String, Set<User.Role>> entry) {
        String methodPattern = entry.getKey().split(":")[0];
        return methodPattern.split("\\|");
    }

    private boolean isPermissionGranted(Optional<Map.Entry<String, Set<User.Role>>> thisPermissionMapping,
                                        HttpServletRequest request) {
        boolean permissionGranted = true;
        if (thisPermissionMapping.isPresent()) {
            User user = (User) request.getSession().getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
            Set<User.Role> userRoles = user.getRoles();
            Set<User.Role> legitRoles = thisPermissionMapping.get().getValue();

            permissionGranted = hasUserLegitRole(userRoles, legitRoles);
        }

        return permissionGranted;
    }

    private boolean hasUserLegitRole(Set<User.Role> userRoles, Set<User.Role> legitRoles) {
        Set<User.Role> userLegitRoles = new HashSet<>(legitRoles);
        userLegitRoles.retainAll(userRoles);

        return !userLegitRoles.isEmpty();
    }
}
