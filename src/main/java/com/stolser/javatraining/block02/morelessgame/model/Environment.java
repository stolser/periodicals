package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.view.ConsoleViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.controller.ConsoleInputReader;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

/**
 * A convenient class encapsulating classes for working with input and output
 * for this instance of the application.
 */
public class Environment {
    private InputReader inputReader;
    private ViewPrinter viewPrinter;
    private ViewGenerator viewGenerator;

    private Environment(InputReader inputReader, ViewPrinter viewPrinter, ViewGenerator viewGenerator) {
        this.inputReader = inputReader;
        this.viewPrinter = viewPrinter;
        this.viewGenerator = viewGenerator;
    }

    public static Environment newInstance() {
        ViewFactory viewFactory = ConsoleViewFactory.newInstance();
        ViewPrinter viewPrinter = viewFactory.getViewPrinter();
        ViewGenerator viewGenerator = viewFactory.getViewGenerator();
        InputReader inputReader = new ConsoleInputReader(viewPrinter);

        return new Environment(inputReader, viewPrinter, viewGenerator);
    }

    public InputReader getInputReader() {
        return inputReader;
    }

    public ViewPrinter getViewPrinter() {
        return viewPrinter;
    }

    public ViewGenerator getViewGenerator() {
        return viewGenerator;
    }
}
