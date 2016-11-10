package com.stolser.javatraining.controller.utils;

import com.stolser.javatraining.block02.morelessgame.model.game.MoreLessGame;

import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

public final class NumberUtils {
    private static final int LOWER_BOUND_DEFAULT = 0;
    private static final int UPPER_BOUND_DEFAULT = 100;

    private NumberUtils() {}

    /**
     * Generates a pseudo-random integer using an equality:
     * random[min;max] = random[0;max-min] + min.<br />
     * Because the upper limit should be inclusive and standard nexInt() uses an exclusive one
     * we use '+1'.<br />
     * Without explicit parameters uses limits from
     * class {@link MoreLessGame}
     * @param min - an inclusive lower bound;
     * @param max - an inclusive upper bound;
     * @return - a pseudo-random integer number from the range [min;max].
     */
    public static int randomInt(int min, int max) {
        checkArgument(min <= max);
        return (ThreadLocalRandom.current().nextInt(max - min + 1) + min);
    }

    public static int randomInt() {
        return randomInt(LOWER_BOUND_DEFAULT, UPPER_BOUND_DEFAULT);
    }
}
