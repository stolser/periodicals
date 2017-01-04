package com.stolser.javatraining.webproject.controller.request.processor;

import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontendMessage;
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

/**
 * Performs validation of the username, checks the password for correctness, checks that
 * this user is active (not blocked) and if everything is OK, adds this user into the session.
 */
public class SignIn implements RequestProcessor {
    private UserService userService = UserServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        // messages to display on the frontend;
        Map<String, FrontendMessage> messages = new HashMap<>();
        HttpSession session = request.getSession();
        String redirectUri;
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);
        String password = request.getParameter(PASSWORD_PARAM_NAME);

        if (usernameAndPasswordIsNotEmpty(username, password)) {
            Credential credential = userService.findOneCredentialByUserName(username);

            if (thereIsNoUsernameInDb(credential)) {
                messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                        messageFactory.getError(MSG_NO_SUCH_USER_NAME));

                redirectUri = SIGN_IN_URI;
            } else if (passwordIsCorrect(password, credential)) {
                User thisUser = userService.findOneUserByUserName(username);

                if (thisUserIsActive(thisUser)) {
                    redirectUri = signInThisUserAndGetRedirectUri(request, session, thisUser);

                } else {
                    messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                            messageFactory.getError(MSG_ERROR_USER_IS_BLOCKED));

                    redirectUri = SIGN_IN_URI;
                }

            } else {
                messages.put(PASSWORD_PARAM_NAME, messageFactory.getError(MSG_ERROR_WRONG_PASSWORD));
                redirectUri = SIGN_IN_URI;
            }

        } else {
            redirectUri = SIGN_IN_URI;
        }

        session.setAttribute(USERNAME_ATTR_NAME, username);
        session.setAttribute(MESSAGES_ATTR_NAME, messages);
        HttpUtils.sendRedirect(request, response, redirectUri);

        return null;
    }

    private String signInThisUserAndGetRedirectUri(HttpServletRequest request, HttpSession session,
                                                   User thisUser) {
        String redirectUri = getRedirectUri(request, thisUser);
        session.setAttribute(CURRENT_USER_ATTR_NAME, thisUser);
        session.removeAttribute(ORIGINAL_URI_ATTR_NAME);

        return redirectUri;
    }

    private boolean thisUserIsActive(User thisUser) {
        return thisUser.getStatus() == User.Status.ACTIVE;
    }

    private boolean thereIsNoUsernameInDb(Credential credential) {
        return credential == null;
    }

    private boolean passwordIsCorrect(String password, Credential credential) {
        return HttpUtils.getPasswordHash(password)
                .equals(credential.getPasswordHash());
    }

    private String getRedirectUri(HttpServletRequest request, User thisUser) {
        String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
        String defaultUri = thisUser.hasRole(ADMIN_ROLE_NAME) ? ADMIN_PANEL_URI
                : CURRENT_USER_ACCOUNT_URI;

        return (originalUri != null) && (!SIGN_OUT_URI.equals(originalUri))
                ? originalUri : defaultUri;
    }

    private boolean usernameAndPasswordIsNotEmpty(String username, String password) {
        return (username != null) && (password != null) && (!"".equals(username)) && (!"".equals(password));
    }
}
