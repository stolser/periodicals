package com.stolser.javatraining.webproject.controller.request.processor.sign;

import com.stolser.javatraining.webproject.controller.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.UserService;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.nonNull;

/**
 * Performs validation of the username, checks the password for correctness, checks that
 * this user is active (not blocked) and if everything is OK, adds this user into the session.
 */
public final class SignIn implements RequestProcessor {
    private UserService userService = UserServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    private SignIn() {}

    private static class InstanceHolder {
        private static final SignIn INSTANCE = new SignIn();
    }

    public static SignIn getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;

        if (isCredentialCorrect(request)) {
            redirectUri = signInIfUserIsActive(request, messages);
        } else {
            addErrorMessages(messages);
            redirectUri = LOGIN_PAGE;
        }

        setSessionAttributes(request, messages);

        return REDIRECT + redirectUri;
    }

    private boolean isCredentialCorrect(HttpServletRequest request) {
        Credential credential = getCredentialFromDb(request);
        String password = request.getParameter(USER_PASSWORD_PARAM_NAME);

        return nonNull(credential) && isPasswordCorrect(password, credential);
    }

    private Credential getCredentialFromDb(HttpServletRequest request) {
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);
        return userService.findOneCredentialByUserName(username);
    }

    private boolean isPasswordCorrect(String password, Credential credential) {
        return HttpUtils.getPasswordHash(password)
                .equals(credential.getPasswordHash());
    }

    private String signInIfUserIsActive(HttpServletRequest request, Map<String, FrontendMessage> messages) {
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);
        User currentUser = userService.findOneUserByUserName(username);
        String redirectUri;

        if (isUserActive(currentUser)) {
            redirectUri = signInUserAndGetRedirectUri(request, currentUser);
        } else {
            redirectUri = LOGIN_PAGE;
            messages.put(SIGN_IN_USERNAME_PARAM_NAME, messageFactory.getError(MSG_ERROR_USER_IS_BLOCKED));
        }

        return redirectUri;
    }

    private boolean isUserActive(User currentUser) {
        return currentUser.getStatus() == User.Status.ACTIVE;
    }

    private String signInUserAndGetRedirectUri(HttpServletRequest request, User currentUser) {
        HttpSession session = request.getSession();
        String redirectUri = getRedirectUri(request, currentUser);
        session.setAttribute(CURRENT_USER_ATTR_NAME, currentUser);
        session.removeAttribute(ORIGINAL_URI_ATTR_NAME);

        return redirectUri;
    }

    private void addErrorMessages(Map<String, FrontendMessage> messages) {
        messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));
        messages.put(USER_PASSWORD_PARAM_NAME, messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));
    }

    private String getRedirectUri(HttpServletRequest request, User currentUser) {
        String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
        String defaultUri = currentUser.hasRole(User.Role.ADMIN) ? ADMIN_PANEL_URI
                : CURRENT_USER_ACCOUNT_URI;

        return (nonNull(originalUri)) && (!SIGN_OUT_URI.equals(originalUri))
                ? originalUri : defaultUri;
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> messages) {
        HttpSession session = request.getSession();
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);

        session.setAttribute(USERNAME_ATTR_NAME, username);
        session.setAttribute(MESSAGES_ATTR_NAME, messages);
    }
}
