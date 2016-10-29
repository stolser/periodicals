package com.stolser.javatraining.block02.morelessgame.view;

import java.io.*;
import java.util.Locale;

class ViewPrinterImpl implements ViewPrinter {
    private Locale locale;
    private PrintWriter output;

    public ViewPrinterImpl(OutputStream output) {
        this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)));
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
