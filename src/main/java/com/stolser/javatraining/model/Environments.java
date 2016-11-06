package com.stolser.javatraining.model;

import com.stolser.javatraining.controller.ConsoleInputReader;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.view.ConsoleViewFactory;
import com.stolser.javatraining.view.ViewFactory;
import com.stolser.javatraining.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

public class Environments {

    public static Environment newConsoleEnvironment() {
        ViewFactory viewFactory = ConsoleViewFactory.newInstance();
        ViewPrinter viewPrinter = viewFactory.getViewPrinter();
        ViewGenerator viewGenerator = viewFactory.getViewGenerator();
        InputReader inputReader = new ConsoleInputReader(viewPrinter);

        return new Environment(inputReader, viewPrinter, viewGenerator);
    }
}
