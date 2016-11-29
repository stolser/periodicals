package com.stolser.javatraining.webproject.controller.validator;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
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

import static com.stolser.javatraining.webproject.controller.LoginServlet.MESSAGES_ATTR_NAME;

public class ValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String paramName = request.getParameter("paramName");
        String paramValue = request.getParameter("paramValue");
        System.out.println("paramName = " + paramName + "; paramValue = " + paramValue);

        removeMessagesForCurrentParam(session, paramName);

        ValidationResult result = ValidatorFactory.getInstance().newValidator(paramName)
                .validate(paramValue, request);

        Locale locale = getLocaleFromSession(session);

        ResourceBundle bundle = ResourceBundle.getBundle(ApplicationResources.VALIDATION_BUNDLE_PATH, locale);
        System.out.println("locale = " + locale + "; bundle = " + bundle);

        String localizedMessage = bundle.getString(result.getMessageKey());
        int statusCode = result.getStatusCode();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        System.out.println("statusCode = " + statusCode);
        System.out.println("localizedMessage = " + localizedMessage);

        try {
            jsonResponse.put("statusCode", statusCode);
            jsonResponse.put("validationMessage", localizedMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonResponse.toString() = " + jsonResponse.toString());

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
        Object localeAttr = session.getAttribute("language");
        Locale locale;

        if (localeAttr instanceof Locale) {
            locale = (Locale) localeAttr;
        } else {
            locale = ApplicationResources.SystemLocale.valueOf(((String) localeAttr).toUpperCase())
                    .getLocale();
        }

        return locale;
    }
}
