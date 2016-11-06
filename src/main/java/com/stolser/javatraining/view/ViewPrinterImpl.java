package com.stolser.javatraining.view;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

class ViewPrinterImpl implements ViewPrinter {
    private Locale locale;
    private PrintStream output;

    public ViewPrinterImpl(PrintStream output) {
        this.output = output;
        this.locale = DEFAULT_LOCALE;
    }

    @Override
    public void printlnString(String string) {
        output.println(string);
    }

    @Override
    public void printString(String string) {
        output.print(string);
    }

    @Override
    public void printlnMessageWithKey(String bundleName, String key) {
        output.println(getMessageWithKey(bundleName, key));
    }

    @Override
    public void printMessageWithKey(String bundleName, String key) {
        output.print(getMessageWithKey(bundleName, key));
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getMessageWithKey(String bundleName, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/"+ bundleName, locale);
        return bundle.getString(key);
    }

    @Override
    public String getLocalizedNumber(Number number) {
        return NumberFormat.getInstance(locale).format(number);
    }
}
