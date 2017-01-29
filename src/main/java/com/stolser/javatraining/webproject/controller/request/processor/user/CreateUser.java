package com.stolser.javatraining.webproject.controller.request.processor.user;

import com.stolser.javatraining.webproject.controller.form.validator.ValidatorFactory;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.nonNull;

public class CreateUser implements RequestProcessor {
    private UserService userService = UserServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    private CreateUser() {}

    private static class InstanceHolder {
        private static final CreateUser INSTANCE = new CreateUser();
    }

    public static CreateUser getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        HttpSession session = request.getSession();
        String redirectUri = SIGN_UP_URI;

        String username = request.getParameter(SIGN_UP_USERNAME_PARAM_NAME);
        String userEmail = request.getParameter(USER_EMAIL_PARAM_NAME);
        String password = request.getParameter(USER_PASSWORD_PARAM_NAME);
        String repeatPassword = request.getParameter(USER_REPEAT_PASSWORD_PARAM_NAME);
        User.Role userRole = User.Role.valueOf(request.getParameter(USER_ROLE_PARAM_NAME).toUpperCase());

        if (!arePasswordsValidAndEqual(password, repeatPassword)) {
            formMessages.put(USER_PASSWORD_PARAM_NAME,
                    messageFactory.getError(MSG_VALIDATION_PASSWORDS_ARE_NOT_EQUAL));
        } else if (usernameExistsInDb(username)) {
            formMessages.put(SIGN_UP_USERNAME_PARAM_NAME,
                    messageFactory.getError(USERNAME_IS_NOT_UNIQUE_TRY_ANOTHER_ONE));
        } else {
            boolean isNewUserCreated = createUser(username, userEmail, password, userRole);
            if (isNewUserCreated) {
                redirectUri = LOGIN_PAGE;
            } else {
                generalMessages.add(messageFactory.getError(MSG_NEW_USER_WAS_NOT_CREATED_ERROR));
            }
        }

        if (SIGN_UP_URI.equals(redirectUri)) {
            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            session.setAttribute(USERNAME_ATTR_NAME, username);
            session.setAttribute(USER_ROLE_ATTR_NAME, userRole);
            session.setAttribute(USER_EMAIL_ATTR_NAME, userEmail);
            session.setAttribute(MESSAGES_ATTR_NAME, formMessages);
        }

        return REDIRECT + redirectUri;
    }

    private boolean createUser(String username, String userEmail, String password, User.Role userRole) {
        Credential.Builder credentialBuilder = new Credential.Builder();
        credentialBuilder.setUserName(username)
                .setPasswordHash(HttpUtils.getPasswordHash(password));

        User.Builder userBuilder = new User.Builder();
        userBuilder.setStatus(User.Status.ACTIVE);
        userBuilder.setEmail(userEmail);

        return userService.createNewUser(userBuilder.build(), credentialBuilder.build(), userRole);
    }

    private boolean arePasswordsValidAndEqual(String password, String repeatPassword) {
        int validationResult = ValidatorFactory.getUserPasswordValidator().validate(password, null).getStatusCode();
        return (validationResult == STATUS_CODE_SUCCESS) && password.equals(repeatPassword);
    }

    private boolean usernameExistsInDb(String username) {
        return nonNull(userService.findOneCredentialByUserName(username));
    }
}
