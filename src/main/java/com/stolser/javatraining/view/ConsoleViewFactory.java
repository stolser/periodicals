package com.stolser.javatraining.view;

/**
 * An implementation of ViewFactory that returns classes for generating information
 * and displaying messages to the standard {@code System.out}.
 */
public final class ConsoleViewFactory implements ViewFactory {
    private ViewPrinter viewPrinter;
    private ViewGenerator viewGenerator;

    private ConsoleViewFactory(){
        viewPrinter = new ViewPrinterImpl(System.out);
        viewGenerator = new ViewGeneratorImpl(viewPrinter);
    }

    @Override
    public ViewGenerator getViewGenerator() {
        return viewGenerator;
    }

    @Override
    public ViewPrinter getViewPrinter() {
        return viewPrinter;
    }

    private static class InstanceHolder {
        static final ConsoleViewFactory FACTORY_INSTANCE = new ConsoleViewFactory();
    }

    public static ConsoleViewFactory getInstance() {
        return InstanceHolder.FACTORY_INSTANCE;
    }
}
