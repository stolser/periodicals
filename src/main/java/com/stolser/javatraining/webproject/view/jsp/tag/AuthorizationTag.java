package com.stolser.javatraining.webproject.view.jsp.tag;


import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Allows specifying two sets of roles that a user must have and must not have in order to
 * see the content of this tag.
 */
public class AuthorizationTag extends TagSupport {
    private String mustHaveRoles;
    private String mustNotHaveRoles;
    private User user;

    @Override
    public int doStartTag() throws JspException {
        user = getUserFromSession();

        if (nonNull(user)
                && hasUserLegitRoles()
                && hasUserNoProhibitedRoles()) {

            return Tag.EVAL_BODY_INCLUDE;
        }

        return Tag.SKIP_BODY;
    }

    private User getUserFromSession() {
        return (User) pageContext.getSession()
                .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);
    }

    private boolean hasUserLegitRoles() {
        if ("*".equals(mustHaveRoles)) {
            return true;

        } else {
            Set<User.Role> legitRoles = parseLegitRoles();
            Set<User.Role> userRoles = new HashSet<>(user.getRoles());

            userRoles.retainAll(legitRoles);

            return !userRoles.isEmpty();
        }
    }

    private Set<User.Role> parseLegitRoles() {
        Set<User.Role> legitRoles = new HashSet<>();

        if (nonNull(mustHaveRoles)) {
            legitRoles.addAll(Arrays.asList(mustHaveRoles.split(" "))
                    .stream()
                    .map(roleStr -> User.Role.valueOf(roleStr.toUpperCase()))
                    .collect(Collectors.toList()));
        }

        return legitRoles;
    }

    private boolean hasUserNoProhibitedRoles() {
        if ("*".equals(mustNotHaveRoles)) {
            return false;

        } else {
            Set<User.Role> prohibitedRoles = parseProhibitedRoles();
            Set<User.Role> userRoles = new HashSet<>(user.getRoles());
            userRoles.retainAll(prohibitedRoles);

            return userRoles.isEmpty();
        }
    }

    private Set<User.Role> parseProhibitedRoles() {
        Set<User.Role> prohibitedRoles = new HashSet<>();

        if (nonNull(mustNotHaveRoles)) {
            prohibitedRoles.addAll(Arrays.asList(mustNotHaveRoles.split(" "))
                    .stream()
                    .map(roleStr -> User.Role.valueOf(roleStr.toUpperCase()))
                    .collect(Collectors.toList()));
        }

        return prohibitedRoles;
    }

    public String getMustHaveRoles() {
        return mustHaveRoles;
    }

    public void setMustHaveRoles(String mustHaveRoles) {
        this.mustHaveRoles = mustHaveRoles;
    }

    public String getMustNotHaveRoles() {
        return mustNotHaveRoles;
    }

    public void setMustNotHaveRoles(String mustNotHaveRoles) {
        this.mustNotHaveRoles = mustNotHaveRoles;
    }
}
