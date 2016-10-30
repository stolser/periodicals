package com.stolser.javatraining.block02.morelessgame.view;

import java.io.*;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String getMessageWithKey(String bundleName, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/"+ bundleName, locale);
        return bundle.getString(key);
    }
}
