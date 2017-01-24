package com.stolser.javatraining.webproject.controller.request.processor.sign;

import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontendMessage;
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
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

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
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        HttpSession session = request.getSession();
        String redirectUri = LOGIN_PAGE;
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);
        String password = request.getParameter(USER_PASSWORD_PARAM_NAME);
        Credential credential = userService.findOneCredentialByUserName(username);

        if ((credential == null) || !isPasswordCorrect(password, credential)) {
            messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                    messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));
            messages.put(USER_PASSWORD_PARAM_NAME, messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));

        } else {
            User currentUser = userService.findOneUserByUserName(username);

            if (isThisUserActive(currentUser)) {
                redirectUri = signInThisUserAndGetRedirectUri(request, session, currentUser);
            } else {
                messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                        messageFactory.getError(MSG_ERROR_USER_IS_BLOCKED));
            }
        }

        session.setAttribute(USERNAME_ATTR_NAME, username);
        session.setAttribute(MESSAGES_ATTR_NAME, messages);
        HttpUtils.sendRedirect(request, response, redirectUri);

        return Optional.empty();
    }

    private String signInThisUserAndGetRedirectUri(HttpServletRequest request, HttpSession session,
                                                   User currentUser) {
        String redirectUri = getRedirectUri(request, currentUser);
        session.setAttribute(CURRENT_USER_ATTR_NAME, currentUser);
        session.removeAttribute(ORIGINAL_URI_ATTR_NAME);

        return redirectUri;
    }

    private boolean isThisUserActive(User currentUser) {
        return currentUser.getStatus() == User.Status.ACTIVE;
    }

    private boolean isPasswordCorrect(String password, Credential credential) {
        return HttpUtils.getPasswordHash(password)
                .equals(credential.getPasswordHash());
    }

    private String getRedirectUri(HttpServletRequest request, User currentUser) {
        String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
        String defaultUri = currentUser.hasRole(User.Role.ADMIN) ? ADMIN_PANEL_URI
                : CURRENT_USER_ACCOUNT_URI;

        return (originalUri != null) && (!SIGN_OUT_URI.equals(originalUri))
                ? originalUri : defaultUri;
    }
}
