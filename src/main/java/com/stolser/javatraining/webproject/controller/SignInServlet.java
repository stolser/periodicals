package com.stolser.javatraining.webproject.controller;

import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.model.entity.user.Login;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.user.UserService;
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
import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.ORIGINAL_URI_ATTR_NAME;

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

        if ((username != null) && (password != null)
                && (!"".equals(username)) && (!"".equals(password))) {
            String passwordHash = getPasswordHash(password);
            UserService userService = UserService.getInstance();

            Login login = userService.findOneLoginByUserName(username);

            if (login == null) {
                messages.put(SIGN_IN_USERNAME_PARAM_NAME,
                        new FrontendMessage("validation.noSuchUserName",
                        FrontendMessage.MessageType.ERROR));

                redirectUri = LOGIN_HREF;
            } else if (passwordHash.equals(login.getPasswordHash())) {
                User thisUser = userService.findOneUserByUserName(username);

                if (thisUser.getStatus() == User.Status.ACTIVE) {
                    request.getSession().setAttribute(CURRENT_USER_ATTR_NAME, thisUser);
                    String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
                    redirectUri = (originalUri != null) ? originalUri : "/backend/";

                    request.getSession().removeAttribute(ORIGINAL_URI_ATTR_NAME);
                    request.getSession().removeAttribute(MESSAGES_ATTR_NAME);

                } else {
                    messages.put(SIGN_IN_USERNAME_PARAM_NAME, new FrontendMessage("error.userIsBlocked",
                            FrontendMessage.MessageType.ERROR));

                    redirectUri = LOGIN_HREF;
                }

            } else {
                messages.put(PASSWORD_PARAM_NAME, new FrontendMessage("error.wrongPassword",
                        FrontendMessage.MessageType.ERROR));

                redirectUri = LOGIN_HREF;
            }

        } else {
            redirectUri = LOGIN_HREF;
        }

        request.getSession().setAttribute(USERNAME_ATTR_NAME, username);
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        response.sendRedirect(redirectUri);

    }

    private String getPasswordHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");

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

        System.out.println("Digest(in hex format):: " + builder.toString());

        return builder.toString();
    }
}
