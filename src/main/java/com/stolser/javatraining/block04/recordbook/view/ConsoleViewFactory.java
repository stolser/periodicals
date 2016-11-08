package com.stolser.javatraining.block04.recordbook.view;

import com.stolser.javatraining.view.ViewPrinter;
import com.stolser.javatraining.view.ViewPrinterImpl;

/**
 * An implementation of ViewFactory that returns classes for generating information
 * and displaying messages to the standard {@code System.out}.
 */
public final class ConsoleViewFactory implements ViewFactory {
    private ViewPrinter viewPrinter;
    private ViewGenerator viewGenerator;

    private ConsoleViewFactory(){
        viewPrinter = new ViewPrinterImpl(System.out);
        viewGenerator = new ViewGeneratorImpl();
    }

    public static ViewFactory newInstance() {
        return new ConsoleViewFactory();
    }

    @Override
    public ViewGenerator getViewGenerator() {
        return viewGenerator;
    }

    @Override
    public ViewPrinter getViewPrinter() {
        return viewPrinter;
    }
}
