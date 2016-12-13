package com.stolser.javatraining.webproject.view.jsp_tag;


import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.model.entity.user.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AuthorizationTag extends TagSupport {
    private String mustHaveRoles;
    private String mustNotHaveRoles;
    private User user;

    @Override
    public int doStartTag() throws JspException {
        user = (User) pageContext.getSession()
                .getAttribute(ApplicationResources.CURRENT_USER_ATTR_NAME);

        if ((user != null)
                && userHasLegitRoles()
                && userHasNoProhibitedRoles()) {

            return Tag.EVAL_BODY_INCLUDE;
        }

        return Tag.SKIP_BODY;
    }

    private boolean userHasLegitRoles() {
        if ("*".equals(mustHaveRoles)) {
            return true;

        } else {
            Set<String> legitRoles = parseLegitRoles();
            Set<String> userRoles = new HashSet<>(user.getRoles());

            userRoles.retainAll(legitRoles);

            return userRoles.size() > 0;
        }
    }

    private Set<String> parseLegitRoles() {
        Set<String> legitRoles = new HashSet<>();

        if (mustHaveRoles != null) {
            legitRoles.addAll(Arrays.asList(mustHaveRoles.split(" ")));
        }

        return legitRoles;
    }

    private boolean userHasNoProhibitedRoles() {
        if ("*".equals(mustNotHaveRoles)) {
            return false;

        } else {
            Set<String> prohibitedRoles = parseProhibitedRoles();
            Set<String> userRoles = new HashSet<>(user.getRoles());
            userRoles.retainAll(prohibitedRoles);

            return userRoles.size() == 0;
        }
    }

    private Set<String> parseProhibitedRoles() {
        Set<String> prohibitedRoles = new HashSet<>();

        if (mustNotHaveRoles != null) {
            prohibitedRoles.addAll(Arrays.asList(mustNotHaveRoles.split(" ")));
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
