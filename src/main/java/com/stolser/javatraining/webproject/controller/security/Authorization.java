package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.request.processor.RequestProviderImpl.*;
import static com.stolser.javatraining.webproject.controller.utils.HttpUtils.filterRequestByHttpMethod;
import static com.stolser.javatraining.webproject.controller.utils.HttpUtils.filterRequestByUri;

/**
 * Encapsulates information about resource access permissions of each type of roles.
 */
final class Authorization {
    private static final Map<String, Set<User.Role>> permissionMapping = new HashMap<>();
    private static final String USERS_URI_WITH_ID = "/backend/users/\\d+";

    static {
        Set<User.Role> admin = new HashSet<>(Collections.singletonList(User.Role.ADMIN));

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

    /**
     * Checks whether a current user has enough permissions to access a requested uri
     * using a current http method.
     *
     * @param request a current http request
     * @return {@code true} - if a current user has enough permissions to perform such a kind of requests,
     * and {@code false} otherwise
     */
    boolean checkPermissions(HttpServletRequest request) {
        if (!isUserIdInUriValid(request)) {
            return false;
        }

        Optional<Map.Entry<String, Set<User.Role>>> accessRestriction = getPermissionMapping(request);

        if (accessRestriction.isPresent()) {
            return isPermissionGranted(accessRestriction.get(), request);
        }

        return true;
    }

    private boolean isUserIdInUriValid(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        Matcher matcher = Pattern.compile(USERS_URI_WITH_ID).matcher(requestUri);
        boolean isValid = true;

        if (matcher.find()) {
            long userIdFromUri = HttpUtils.getFirstIdFromUri(requestUri);
            long userIdFromSession = HttpUtils.getUserIdFromSession(request);

            isValid = userIdFromUri == userIdFromSession;
        }

        return isValid;
    }

    private Optional<Map.Entry<String, Set<User.Role>>> getPermissionMapping(HttpServletRequest request) {
        return permissionMapping.entrySet()
                .stream()
                .filter(entry -> filterRequestByHttpMethod(request, entry.getKey()))
                .filter(entry -> filterRequestByUri(request, entry.getKey()))
                .findFirst();
    }

    private boolean isPermissionGranted(Map.Entry<String, Set<User.Role>> permissionMapping,
                                        HttpServletRequest request) {
        Set<User.Role> userRoles = getUserFromSession(request).getRoles();
        Set<User.Role> legitRoles = permissionMapping.getValue();

        return hasUserLegitRole(userRoles, legitRoles);
    }

    private User getUserFromSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(CURRENT_USER_ATTR_NAME);
    }

    private boolean hasUserLegitRole(Set<User.Role> userRoles, Set<User.Role> legitRoles) {
        Set<User.Role> userLegitRoles = new HashSet<>(legitRoles);
        userLegitRoles.retainAll(userRoles);

        return !userLegitRoles.isEmpty();
    }
}
