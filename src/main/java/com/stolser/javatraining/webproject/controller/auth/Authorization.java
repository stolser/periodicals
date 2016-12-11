package com.stolser.javatraining.webproject.controller.auth;

import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class Authorization {
    private static final Map<String, Set<String>> permissionMapping = new HashMap<>();

    static {
        Set<String> admin = new HashSet<>(Collections.singletonList("admin"));
        Set<String> subscriber = new HashSet<>(Collections.singletonList("subscriber"));

        permissionMapping.put("/backend/users(/\\d*)?", admin);
        permissionMapping.put("/backend/periodicals/createNew/?", admin);
        permissionMapping.put("/backend/periodicals/update/\\d+", admin);
        permissionMapping.put("/backend/periodicals/discarded/delete/?", admin);
        permissionMapping.put("/backend/invoices/?", subscriber);
    }

    private HttpServletRequest request;
    private User user;

    public Authorization(HttpServletRequest request) {
        this.request = request;
    }

    public boolean checkPermissions() {
        String requestURI = request.getRequestURI();
        user = (User) request.getSession().getAttribute("thisUser");


        Optional<Map.Entry<String, Set<String>>> thisPermissionMapping =
                permissionMapping.entrySet()
                        .stream()
                        .filter(entry -> Pattern.matches(entry.getKey(), requestURI))
                        .findFirst();

        boolean permissionGranted = true;
        if (thisPermissionMapping.isPresent()) {
            permissionGranted = userHasLegitRole(thisPermissionMapping.get().getValue());
        }

        return permissionGranted;
    }

    private boolean userHasLegitRole(Set<String> legitRoles) {
        Set<String> userRoles = user.getRoles();
        Set<String> userLegitRoles = new HashSet<>(legitRoles);
        System.out.println("------ User's roles:");
        userRoles.forEach(System.out::println);

        userLegitRoles.retainAll(userRoles);

        System.out.println("------ legit user's roles: ");
        userLegitRoles.forEach(System.out::println);

        return (userLegitRoles.size() != 0);
    }
}
