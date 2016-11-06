package com.stolser.javatraining.block04.recordbook;

import com.stolser.javatraining.block04.recordbook.model.RecordBookApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordBookRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordBookRunner.class);

    public static void main( String[] args ) {
        LOGGER.debug("Loading a RecordBook application...");

        new RecordBookApp().start();

        LOGGER.debug("The RecordBook app successfully stopped.");
    }
}
