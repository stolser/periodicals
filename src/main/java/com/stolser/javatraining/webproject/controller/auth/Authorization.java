package com.stolser.javatraining.webproject.controller.auth;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ADMIN_ROLE_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.SUBSCRIBER_ROLE_NAME;

class Authorization {
    private static final Map<String, Set<String>> permissionMapping = new HashMap<>();

    static {
        Set<String> admin = new HashSet<>(Collections.singletonList(ADMIN_ROLE_NAME));
        Set<String> subscriber = new HashSet<>(Collections.singletonList(SUBSCRIBER_ROLE_NAME));

        permissionMapping.put("/backend/users(/\\d*)?", admin);
        permissionMapping.put("/backend/periodicals/createNew/?", admin);
        permissionMapping.put("/backend/periodicals/\\d+/update/?", admin);
        permissionMapping.put("/backend/periodicals/discarded/?", admin);
        permissionMapping.put("/backend/adminPanel/?", admin);
        permissionMapping.put("/backend/invoices/?", subscriber);
    }

    private Authorization() {}

    private static class InstanceHolder {
        private static final Authorization INSTANCE = new Authorization();
    }

    static Authorization getInstance() {
        return InstanceHolder.INSTANCE;
    }

    boolean checkPermissions(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        // todo: add 'GET|POST';

        Optional<Map.Entry<String, Set<String>>> thisPermissionMapping =
                permissionMapping.entrySet()
                        .stream()
                        .filter(entry -> Pattern.matches(entry.getKey(), requestURI))
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
//        System.out.println("------ User's roles:");
//        userRoles.forEach(System.out::println);

        userLegitRoles.retainAll(userRoles);

//        System.out.println("------ legit user's roles: ");
//        userLegitRoles.forEach(System.out::println);

        return (userLegitRoles.size() != 0);
    }
}
