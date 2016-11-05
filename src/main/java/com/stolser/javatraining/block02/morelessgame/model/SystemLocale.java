package com.stolser.javatraining.block02.morelessgame.model;

import java.util.Locale;

public enum SystemLocale {
    EN_US("locale.en_US.name", 1, Locale.US),
    RU_RU("locale.ru_RU.name", 2, new Locale("ru", "RU"));

    private String systemName;
    private int menuOption;
    private Locale locale;

    SystemLocale(String systemName, int menuOption, Locale locale) {
        this.systemName = systemName;
        this.menuOption = menuOption;
        this.locale = locale;
    }

    public static Locale getLocaleByMenuOption(int menuOption) {
        for (SystemLocale systemLocale: SystemLocale.values()) {
            if (systemLocale.getMenuOption() == menuOption) {
                return systemLocale.getLocale();
            }
        }

        return null;
    }

    public String getSystemName() {
        return systemName;
    }

    public int getMenuOption() {
        return menuOption;
    }

    public Locale getLocale() {
        return locale;
    }
}
