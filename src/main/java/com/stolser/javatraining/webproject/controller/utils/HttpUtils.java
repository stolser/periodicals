package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.security.AccessDeniedException;
import com.stolser.javatraining.webproject.dao.exception.DaoException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.UserService;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.nonNull;

public final class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final String ALGORITHM_NAME = "MD5";
    private static final String EXCEPTION_DURING_GETTING_MESSAGE_DIGEST_FOR_MD5 =
            "Exception during getting MessageDigest for 'MD5'";
    private static final String REDIRECTION_FROM_TO_TEXT = "During redirection from \"%s\" to \"%s\"";
    private static final String URI_MUST_CONTAIN_ID_TEXT = "Uri (%s) must contain id.";
    private static final String EXCEPTION_DURING_REDIRECTION_TEXT =
            "User id = %d. Exception during redirection to '%s'.";
    private static final String NUMBER_REGEX = "\\d+";
    private static UserService userService = UserServiceImpl.getInstance();

    private HttpUtils() {}

    /**
     * Retrieves a current user's id from the session.
     * @return id of the current signed in user or 0 if a user has not been authenticated yet
     */
    public static long getUserIdFromSession(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(CURRENT_USER_ATTR_NAME);
        return nonNull(user) ? user.getId() : 0;
    }

    /**
     * Retrieves a user object from the db for the current user from the request.
     */
    public static User getCurrentUserFromFromDb(HttpServletRequest request) {
        return userService.findOneById(getUserIdFromSession(request));
    }

    /**
     * Creates a new periodical using the data from the request.
     */
    public static Periodical getPeriodicalFromRequest(HttpServletRequest request) {
        Periodical.Builder periodicalBuilder = new Periodical.Builder();
        periodicalBuilder.setId(Long.parseLong(request.getParameter(ENTITY_ID_PARAM_NAME)))
                .setName(request.getParameter(PERIODICAL_NAME_PARAM_NAME))
                .setCategory(PeriodicalCategory.valueOf(
                        request.getParameter(PERIODICAL_CATEGORY_PARAM_NAME).toUpperCase()))
                .setPublisher(request.getParameter(PERIODICAL_PUBLISHER_PARAM_NAME))
                .setDescription(request.getParameter(PERIODICAL_DESCRIPTION_PARAM_NAME).trim())
                .setOneMonthCost(Long.parseLong(request.getParameter(PERIODICAL_COST_PARAM_NAME)))
                .setStatus(Periodical.Status.valueOf(
                        (request.getParameter(PERIODICAL_STATUS_PARAM_NAME)).toUpperCase()));

        return periodicalBuilder.build();
    }

    /**
     * Tries to find the first number in the uri.
     */
    public static int getFirstIdFromUri(String uri) {
        Matcher matcher = Pattern.compile(NUMBER_REGEX).matcher(uri);

        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format(URI_MUST_CONTAIN_ID_TEXT, uri));
        }

        return Integer.parseInt(matcher.group());
    }

    /**
     * Sets a session scoped attribute 'messages'.
     */
    public static void addMessagesToSession(HttpServletRequest request,
                                            Map<String, List<FrontendMessage>> frontMessageMap) {
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, frontMessageMap);
    }

    /**
     * Adds general messages to the session.
     */
    public static void addGeneralMessagesToSession(HttpServletRequest request,
                                                   List<FrontendMessage> generalMessages) {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(GENERAL_MESSAGES_FRONT_BLOCK_NAME, generalMessages);
        HttpUtils.addMessagesToSession(request, frontMessageMap);
    }

    /**
     * Sends a redirect on this response.
     */
    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                                    String redirectUri) {
        try {
            response.sendRedirect(redirectUri);

        } catch (IOException e) {
            String message = String.format(EXCEPTION_DURING_REDIRECTION_TEXT,
                    HttpUtils.getUserIdFromSession(request), redirectUri);

            throw new RuntimeException(message, e);
        }
    }

    /**
     * Returns an appropriate view name for this exception.
     */
    public static String getErrorViewName(Throwable exception) {
        if (exception instanceof DaoException) {
            return STORAGE_EXCEPTION_PAGE_VIEW_NAME;
        }

        if (exception instanceof NoSuchElementException) {
            return PAGE_404_VIEW_NAME;
        }

        if (exception instanceof AccessDeniedException) {
            return ACCESS_DENIED_PAGE_VIEW_NAME;
        }

        return GENERAL_ERROR_PAGE_VIEW_NAME;
    }

    /**
     * Returns a hash for this password.
     */
    public static String getPasswordHash(String password) {
        try {
            return convertPasswordIntoHash(password);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(EXCEPTION_DURING_GETTING_MESSAGE_DIGEST_FOR_MD5, e);
            throw new RuntimeException();
        }
    }

    private static String convertPasswordIntoHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM_NAME);

        md.update(password.getBytes());
        byte[] byteData = md.digest();

        StringBuilder builder = new StringBuilder();
        for (byte aByteData : byteData) {
            builder.append(Integer.toString((aByteData & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return builder.toString();
    }

    public static boolean filterRequestByHttpMethod(HttpServletRequest request, String mapping) {
        String methodPattern = mapping.split(METHODS_URI_SEPARATOR)[0];
        String[] methods = methodPattern.split(METHOD_METHOD_SEPARATOR);
        String requestMethod = request.getMethod().toUpperCase();

        return Arrays.asList(methods).contains(requestMethod);
    }

    public static boolean filterRequestByUri(HttpServletRequest request, String mapping) {
        String urlPattern = mapping.split(METHODS_URI_SEPARATOR)[1];
        String requestUri = request.getRequestURI();

        return Pattern.matches(urlPattern, requestUri);
    }
}
