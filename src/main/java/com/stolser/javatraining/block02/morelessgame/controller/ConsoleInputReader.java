package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private Scanner scanner;
    private ResourceBundle generalMessages;
    private ViewPrinter output;

    public ConsoleInputReader(ViewPrinter output) {
        this.output = output;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int readIntValue() {
        generalMessages = ResourceBundle.getBundle("generalMessages", output.getLocale());
        while( ! scanner.hasNextInt()) {
            output.printMessage(generalMessages.getString("input.integer.error"));
            scanner.next();
        }

        return scanner.nextInt();
    }
}
