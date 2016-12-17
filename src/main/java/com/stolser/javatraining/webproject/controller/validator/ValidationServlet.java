package com.stolser.javatraining.webproject.controller.validator;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ValidationServlet extends HttpServlet {
    private ValidatorFactory validatorFactory = ValidatorFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String paramName = request.getParameter(PARAM_NAME);
        String paramValue = request.getParameter(PARAM_VALUE);

        removeMessagesForCurrentParam(session, paramName);

        ValidationResult result = validatorFactory.newValidator(paramName)
                .validate(paramValue, request);

        Locale locale = getLocaleFromSession(session);

        ResourceBundle bundle = ResourceBundle.getBundle(VALIDATION_BUNDLE_PATH, locale);

        String localizedMessage = bundle.getString(result.getMessageKey());
        int statusCode = result.getStatusCode();

        response.setContentType(JSON_CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);

        PrintWriter writer = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put(STATUS_CODE_JSON_RESPONSE, statusCode);
            jsonResponse.put(VALIDATION_MESSAGE_JSON_RESPONSE, localizedMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        writer.println(jsonResponse.toString());
        writer.flush();

    }

    private void removeMessagesForCurrentParam(HttpSession session, String paramName) {
        Map frontEndMessages = (Map)session.getAttribute(MESSAGES_ATTR_NAME);

        if (frontEndMessages != null) {
            frontEndMessages.remove(paramName);
        }
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
}
