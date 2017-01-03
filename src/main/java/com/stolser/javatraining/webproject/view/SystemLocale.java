package com.stolser.javatraining.webproject.view;

import java.util.Locale;

public enum SystemLocale {
    EN_EN(Locale.ENGLISH),
    RU_RU(new Locale("ru", "RU")),
    UK_UA(new Locale("uk", "UA"));

    private Locale locale;

    SystemLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
