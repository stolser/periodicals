package com.stolser.javatraining.webproject.controller.form_validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Validates a parameter from the request and sends a json with the validation result.
 * Can be used for ajax validation of input field values.
 */
public class ValidationServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationServlet.class);
    private static final String EXCEPTION_DURING_PUTTING_VALUES_INTO_JSON_OBJECT = "Exception during putting values into json object.";
    private ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String paramName = request.getParameter(PARAM_NAME);
        String paramValue = request.getParameter(PARAM_VALUE);

        removeMessagesForCurrentParam(session, paramName);
        customizeResponse(response);

        JSONObject jsonResponse = new JSONObject();
        try {
            ValidationResult result = validatorFactory.newValidator(paramName).validate(paramValue, request);
            jsonResponse.put(STATUS_CODE_JSON_RESPONSE, result.getStatusCode());
            jsonResponse.put(VALIDATION_MESSAGE_JSON_RESPONSE, getLocalizedMessage(session, result));
        } catch (JSONException e) {
            LOGGER.error(EXCEPTION_DURING_PUTTING_VALUES_INTO_JSON_OBJECT, e);
        }

        PrintWriter writer = response.getWriter();
        writer.println(jsonResponse.toString());
        writer.flush();
    }

    private void removeMessagesForCurrentParam(HttpSession session, String paramName) {
        Map frontEndMessages = (Map)session.getAttribute(MESSAGES_ATTR_NAME);

        if (frontEndMessages != null) {
            frontEndMessages.remove(paramName);
        }
    }

    private void customizeResponse(HttpServletResponse response) {
        response.setContentType(JSON_CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
    }

    private Locale getLocaleFromSession(HttpSession session) {
        Object localeAttr = session.getAttribute(LANGUAGE_ATTR_NAME);
        Locale locale;

        if (localeAttr instanceof Locale) {
            locale = (Locale) localeAttr;
        } else {
            locale = SystemLocale.valueOf(((String) localeAttr).toUpperCase())
                    .getLocale();
        }

        return locale;
    }

    private String getLocalizedMessage(HttpSession session, ValidationResult result) {
        return ResourceBundle.getBundle(VALIDATION_BUNDLE_PATH, getLocaleFromSession(session))
                .getString(result.getMessageKey());
    }
}
