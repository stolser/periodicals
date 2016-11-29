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

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.ORIGINAL_URI_ATTR_NAME;

public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);
    public static final String MESSAGES_ATTR_NAME = "messages";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;
        String username = request.getParameter("signInUsername");
        String password = request.getParameter("password");

        if ((username != null) && (password != null)
                && (!"".equals(username)) && (!"".equals(password))) {
            String passwordHash = getPasswordHash(password);
            UserService userService = UserService.getInstance();

            Login login = userService.findOneLoginByUserName(username);

            if (login == null) {
                messages.put("signInUsername", new FrontendMessage("signInUsername", "validation.noSuchUserName",
                        FrontendMessage.MessageType.ERROR));

                redirectUri = "/login.jsp";
            } else if (passwordHash.equals(login.getPasswordHash())) {
                User thisUser = userService.findOneUserByUserName(username);

                if (thisUser.getStatus() == User.Status.ACTIVE) {
                    request.getSession().setAttribute(CURRENT_USER_ATTR_NAME, thisUser);
                    String originalUri = (String) request.getSession().getAttribute(ORIGINAL_URI_ATTR_NAME);
                    redirectUri = (originalUri != null) ? originalUri : "/adminPanel";
                    request.getSession().removeAttribute(ORIGINAL_URI_ATTR_NAME);
                    request.getSession().removeAttribute(MESSAGES_ATTR_NAME);

                } else {
                    messages.put("signInUsername", new FrontendMessage("signInUsername", "error.userIsBlocked",
                            FrontendMessage.MessageType.ERROR));

                    redirectUri = "/login.jsp";
                }

            } else {
                messages.put("password", new FrontendMessage("password", "error.wrongPassword",
                        FrontendMessage.MessageType.ERROR));

                redirectUri = "/login.jsp";
            }

        } else {
            redirectUri = "/login.jsp";
        }

        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        response.sendRedirect(redirectUri);

    }

    private String getPasswordHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            LOGGER.debug("Exception during getting MessageDigest for 'MD5'", e);
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
