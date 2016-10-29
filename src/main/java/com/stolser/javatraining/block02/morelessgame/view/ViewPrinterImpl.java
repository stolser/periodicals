package com.stolser.javatraining.block02.morelessgame.view;

import java.io.*;

class ViewPrinterImpl implements ViewPrinter {
    private PrintWriter output;

    public ViewPrinterImpl(OutputStream output) {
        this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)));
    }

    public void printMessage(String message) {
        output.println(message);
    }
}
