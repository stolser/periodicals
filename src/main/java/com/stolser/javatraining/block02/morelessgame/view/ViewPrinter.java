package com.stolser.javatraining.block02.morelessgame.view;

import java.util.Locale;

public interface ViewPrinter {
    Locale DEFAULT_LOCALE = new Locale("en", "US");

    void printMessage(String message);
    Locale getLocale();
    void setLocale(Locale locale);
}
