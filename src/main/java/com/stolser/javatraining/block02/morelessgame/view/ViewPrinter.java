package com.stolser.javatraining.block02.morelessgame.view;

import java.util.Locale;

/**
 * Implements The Adapter Design Pattern.
 */
public interface ViewPrinter {
    Locale DEFAULT_LOCALE = new Locale("en", "US");
    String RANDOM_MAX_OUT_OF_LIMITS_EXCEPTION_TEXT = "The new value for Random Max is out of limits.";

    void printlnString(String string);
    void printString(String string);
    void printlnMessageWithKey(String bundleName, String key);
    void printMessageWithKey(String bundleName, String key);
    void setLocale(Locale locale);
    String getMessageWithKey(String bundleName, String key);
}
