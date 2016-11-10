package com.stolser.javatraining.controller.validate;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegexValidator {
    private static final Map<String, Pattern> patternCache = new HashMap<>();

    public static boolean validateString(String regex, String value) {
        Pattern pattern = getPatternFromCache(regex);

        return pattern.matcher(value).matches();
    }

    private static Pattern getPatternFromCache(String regex) {
        Pattern pattern = patternCache.get(regex);

        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patternCache.put(regex, pattern);
        }

        return pattern;
    }
}
