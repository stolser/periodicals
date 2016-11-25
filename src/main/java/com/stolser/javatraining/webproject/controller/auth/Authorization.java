package com.stolser.javatraining.webproject.controller.auth;

import com.stolser.javatraining.webproject.model.entity.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class Authorization {
    private static final Map<String, Set<String>> permissionMapping = new HashMap<>();

    static {
        Set<String> onlyAdmin = new HashSet<>(Collections.singletonList("admin"));

        permissionMapping.put("/admin/users(/.*)?", onlyAdmin);
    }

    private HttpServletRequest request;
    private Subject subject;
    private User user;

    public Authorization(HttpServletRequest request) {
        this.request = request;
    }

    public boolean checkPermissions() {
        subject = SecurityUtils.getSubject();
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

        legitRoles.retainAll(userRoles);

        return (legitRoles.size() != 0);
    }
}
