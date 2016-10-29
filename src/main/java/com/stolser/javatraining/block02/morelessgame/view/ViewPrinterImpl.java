package com.stolser.javatraining.block02.morelessgame.view;

import java.io.*;
import java.util.Locale;

class ViewPrinterImpl implements ViewPrinter {
    private Locale locale;
    private PrintStream output;

    public ViewPrinterImpl(PrintStream output) {
        this.output = output;
        this.locale = DEFAULT_LOCALE;
    }

    public void printMessage(String message) {
        output.println(message);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
