package com.stolser.javatraining.view;

import com.stolser.javatraining.model.SystemLocale;

import java.util.Locale;

/**
 * Implements The Adapter Design Pattern.
 */
public interface ViewPrinter {
    Locale DEFAULT_LOCALE = SystemLocale.EN_US.getLocale();

    /**
     * Prints a string with a new line.
     * @param string - a string to printed.
     */
    void printlnString(String string);

    /**
     * Prints a string without a new line.
     * @param string - a string to printed.
     */
    void printString(String string);

    /**
     * Prints an localized message from a properties file.
     * @param bundleName the name of the properties file from which a message with a key is taken.
     * @param key the key of a message to print.
     */
    void printlnMessageWithKey(String bundleName, String key);
    void printMessageWithKey(String bundleName, String key);

    /**
     * Sets a new system locale.
     * @param locale a new locale to used in the system. Changes are visible immediately.
     */
    void setLocale(Locale locale);

    /**
     * Returns a localized message with a this key from this source bundle.
     * @param bundleName the name of the bundle name.
     * @param key the key of the message.
     * @return a localized message.
     */
    String getMessageWithKey(String bundleName, String key);

    /**
     * Returns a localized number.
     * @param number the number to be localized.
     * @return a localized number.
     */
    String getLocalizedNumber(Number number);
}
