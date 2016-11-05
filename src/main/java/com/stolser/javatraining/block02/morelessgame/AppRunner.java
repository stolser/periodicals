package com.stolser.javatraining.block02.morelessgame;

import com.stolser.javatraining.block02.morelessgame.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);

    public static void main( String[] args ) {
        LOGGER.debug("Loading a new application...");

        new Application().start();

        LOGGER.debug("The app successfully stopped.");
    }
}
