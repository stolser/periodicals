package com.stolser.javatraining.model;

import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

/**
 * A convenient class encapsulating classes for working with input and output
 * for this instance of the application.
 */
public class Environment {
    private InputReader inputReader;
    private ViewPrinter viewPrinter;
    private ViewGenerator viewGenerator;

    public Environment(InputReader inputReader, ViewPrinter viewPrinter, ViewGenerator viewGenerator) {
        this.inputReader = inputReader;
        this.viewPrinter = viewPrinter;
        this.viewGenerator = viewGenerator;
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
