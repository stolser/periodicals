package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;
import com.stolser.javatraining.webproject.model.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class SignInServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInServlet.class);
    private static final String EXCEPTION_DURING_GETTING_MESSAGE_DIGEST_FOR_MD5 = "Exception during getting MessageDigest for 'MD5'";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;
        String username = request.getParameter(SIGN_IN_USERNAME_PARAM_NAME);
        String password = request.getParameter(PASSWORD_PARAM_NAME);

        if (usernameAndPasswordIsNotEmpty(username, password)) {
            String passwordHash = getPasswordHash(password);
            UserService userService = UserServiceImpl.getInstance();

            Credential credential = userService.findOneCredentialByUserName(username);

            if (credential == null) {
                messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                        new FrontendMessage(MSG_NO_SUCH_USER_NAME,
                        FrontendMessage.MessageType.ERROR));

                redirectUri = SIGN_IN_URI;
            } else if (passwordHash.equals(credential.getPasswordHash())) {
                User thisUser = userService.findOneUserByUserName(username);

                if (thisUser.getStatus() == User.Status.ACTIVE) {
                    request.getSession().setAttribute(CURRENT_USER_ATTR_NAME, thisUser);
                    String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
                    String defaultUri = thisUser.hasRole(ADMIN_ROLE_NAME) ? ADMIN_PANEL_URI
                            : CURRENT_USER_ACCOUNT_URI;
                    redirectUri = (originalUri != null) ? originalUri : defaultUri;

                    request.getSession().removeAttribute(ORIGINAL_URI_ATTR_NAME);
                    request.getSession().removeAttribute(MESSAGES_ATTR_NAME);

                } else {
                    messages.put(SIGN_IN_USERNAME_PARAM_NAME, new FrontendMessage(MSG_ERROR_USER_IS_BLOCKED,
                            FrontendMessage.MessageType.ERROR));

                    redirectUri = SIGN_IN_URI;
                }

            } else {
                messages.put(PASSWORD_PARAM_NAME, new FrontendMessage(MSG_ERROR_WRONG_PASSWORD,
                        FrontendMessage.MessageType.ERROR));

                redirectUri = SIGN_IN_URI;
            }

        } else {
            redirectUri = SIGN_IN_URI;
        }

        request.getSession().setAttribute(USERNAME_ATTR_NAME, username);
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        response.sendRedirect(redirectUri);

    }

    private boolean usernameAndPasswordIsNotEmpty(String username, String password) {
        return (username != null) && (password != null) && (!"".equals(username)) && (!"".equals(password));
    }

    private String getPasswordHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(ALGORITHM_NAME);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(EXCEPTION_DURING_GETTING_MESSAGE_DIGEST_FOR_MD5, e);
            throw new RuntimeException();
        }

        md.update(password.getBytes());
        byte byteData[] = md.digest();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            builder.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return builder.toString();
    }
}
