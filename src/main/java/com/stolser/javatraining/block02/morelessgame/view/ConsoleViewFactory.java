package com.stolser.javatraining.block02.morelessgame.view;

public final class ConsoleViewFactory implements ViewFactory {
    private ConsoleViewFactory(){}

    @Override
    public ViewGenerator getViewGenerator() {
        return new ConsoleViewGenerator();
    }

    @Override
    public ViewPrinter getViewPrinter() {
        return new ViewPrinterImpl(System.out);
    }

    private static class InstanceHolder {
        public static final ConsoleViewFactory FACTORY_INSTANCE = new ConsoleViewFactory();
    }

    public static ConsoleViewFactory getInstance() {
        return InstanceHolder.FACTORY_INSTANCE;
    }
}
