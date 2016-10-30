package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private Scanner scanner;
    private ViewPrinter output;

    public ConsoleInputReader(ViewPrinter output) {
        this.output = output;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int readIntValue() {
        while( ! scanner.hasNextInt()) {
            output.printMessageWithKey("generalMessages", "input.integer.error");
            scanner.next();
        }

        return scanner.nextInt();
    }
}
