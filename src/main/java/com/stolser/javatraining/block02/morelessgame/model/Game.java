package com.stolser.javatraining.block02.morelessgame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    public static final int RANDOM_MAX_LOW_LIMIT = 10;
    public static final int RANDOM_MAX_HIGH_LIMIT = 1000;
    private static int randomMinDefault = 0;
    private static int randomMaxDefault = 100;

    public void start() {
        LOGGER.debug("------------Start a Game...\n" +
                "Default interval: [" + randomMinDefault + ", " + randomMaxDefault + "]\n" +
                "...End the Game.------------");
    }

    public static void setRandomMaxDefault(int newValue) {
        if(newValue < RANDOM_MAX_LOW_LIMIT || newValue > RANDOM_MAX_HIGH_LIMIT)
            throw new IllegalArgumentException();
        randomMaxDefault = newValue;
    }

}
