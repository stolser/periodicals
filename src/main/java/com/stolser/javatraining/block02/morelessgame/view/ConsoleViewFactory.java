package com.stolser.javatraining.block02.morelessgame.view;

import java.io.PrintStream;

public final class ConsoleViewFactory implements ViewFactory {
    private static final PrintStream OUTPUT_STREAM = System.out;
    private ViewPrinter viewPrinter;
    private ViewGenerator viewGenerator;

    private ConsoleViewFactory(){
        viewPrinter = new ViewPrinterImpl(OUTPUT_STREAM);
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
