package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final InputStream INPUT_STREAM = System.in;
    private static final String INPUT_INTEGER_ERROR = "input.integer.error";

    private Scanner scanner;
    private ViewPrinter output;

    public ConsoleInputReader(ViewPrinter output) {
        this.output = output;
        this.scanner = new Scanner(INPUT_STREAM);
    }

    @Override
    public int readIntValue() {
        while( ! scanner.hasNextInt()) {
            output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_INTEGER_ERROR);
            scanner.next();
        }

        return scanner.nextInt();
    }
}