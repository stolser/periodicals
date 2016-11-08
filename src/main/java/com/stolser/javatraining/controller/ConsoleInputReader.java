package com.stolser.javatraining.controller;

import com.stolser.javatraining.view.ViewPrinter;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String INPUT_INTEGER_ERROR = "input.integer.error";

    private Scanner scanner;
    private ViewPrinter output;

    public ConsoleInputReader(ViewPrinter output) {
        this.output = output;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int readIntValue() {
        while(! scanner.hasNextInt()) {
            output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_INTEGER_ERROR);
            scanner.nextLine();
        }

        int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean readYesNoValue() {
        int userIntChoice;

        do {
            output.printString(" Enter 0 - no; 1 - yes: ");

            while (! scanner.hasNextInt()) {
                scanner.nextLine();
            }

            userIntChoice = scanner.nextInt();

        } while ((userIntChoice != 0) && (userIntChoice != 1));

        scanner.nextLine();

        return (userIntChoice == 1);
    }
}