package com.stolser.javatraining.block05.reflection;

import com.stolser.javatraining.block05.reflection.controller.ReflectionController;
import com.stolser.javatraining.view.ViewPrinter;
import com.stolser.javatraining.view.ViewPrinterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionRunner.class);

    public static void main(String[] args) {
        LOGGER.debug("Loading a RecordBook application...");

        ViewPrinter viewPrinter = new ViewPrinterImpl(System.out);
        new ReflectionController(viewPrinter).start();

        LOGGER.debug("The RecordBook app successfully stopped.");
    }
}
