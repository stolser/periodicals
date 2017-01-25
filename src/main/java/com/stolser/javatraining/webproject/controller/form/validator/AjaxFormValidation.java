package com.stolser.javatraining.webproject.controller.form.validator;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.view.SystemLocale;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Validates a parameter from the request and sends a json with the validation result.
 * Can be used for ajax validation of input field values.
 */
public class AjaxFormValidation implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxFormValidation.class);
    private static final String EXCEPTION_DURING_PUTTING_VALUES_INTO_JSON_OBJECT =
            "Exception during putting values into json object.";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String STATUS_CODE_JSON_RESPONSE = "statusCode";
    private static final String VALIDATION_MESSAGE_JSON_RESPONSE = "validationMessage";
    private static final String EXCEPTION_DURING_VALIDATION = "Exception during validation.";

    private AjaxFormValidation() {}

    private static class InstanceHolder {
        private static final AjaxFormValidation INSTANCE = new AjaxFormValidation();
    }

    public static AjaxFormValidation getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String paramName = request.getParameter(PARAM_NAME);
        String paramValue = request.getParameter(PARAM_VALUE);

        removeMessagesForCurrentParam(session, paramName);
        customizeResponse(response);

        try {
            JSONObject jsonResponse = new JSONObject();
            ValidationResult result = ValidatorFactory.newValidator(paramName).validate(paramValue, request);
            jsonResponse.put(STATUS_CODE_JSON_RESPONSE, result.getStatusCode());
            jsonResponse.put(VALIDATION_MESSAGE_JSON_RESPONSE, getLocalizedMessage(session, result));

            writeJsonIntoResponse(response, jsonResponse);
            return Optional.empty();

        } catch (JSONException e) {
            LOGGER.error(EXCEPTION_DURING_PUTTING_VALUES_INTO_JSON_OBJECT, e);
            throw new ValidationProcessorException(EXCEPTION_DURING_PUTTING_VALUES_INTO_JSON_OBJECT, e);
        } catch (IOException e) {
            throw new ValidationProcessorException(EXCEPTION_DURING_VALIDATION, e);
        }
    }

    private void writeJsonIntoResponse(HttpServletResponse response, JSONObject jsonResponse)
            throws IOException {
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
