package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public static final int RANDOM_MAX_LOW_LIMIT = 10;
    public static final int RANDOM_MAX_HIGH_LIMIT = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private ViewPrinter output;
    private ViewGenerator viewGenerator;
    private InputReader input;
    private static int randomMinDefault = 0;
    private static int randomMaxDefault = 100;
    private int nextAttemptSerialNo = 1;
    private List<UserAttempt> userAttempts;
    private Range<Integer> currentRange;
    private int target;
    private boolean targetIsNotHit;

    public Game(Environment environment) {
        output = environment.getViewPrinter();
        viewGenerator = environment.getViewGenerator();
        input = environment.getInputReader();
        target = randomInt();
        userAttempts = new LinkedList<>();
        currentRange = Range.closed(randomMinDefault, randomMaxDefault);
        targetIsNotHit = true;
    }

    enum AttemptResult {
        ATTEMPT_RESULT_TOO_SMALL("too small"),
        ATTEMPT_RESULT_TOO_LARGE("too large"),
        ATTEMPT_RESULT_SCORE("score!");

        private String description;

        AttemptResult(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public void start() {
        LOGGER.debug("Starting a new Game. Default range: {}...", currentRange);

        do {
            int userNumber = getNewNumberFromUser();
            UserAttempt newAttempt = new UserAttempt(nextAttemptSerialNo, currentRange);
            nextAttemptSerialNo++;
            newAttempt.setNumber(userNumber);

            if (userNumber == target) {
                newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_SCORE);
                targetIsNotHit = false;
            } else {
                if(userNumber < target) {
                    newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_TOO_SMALL);
                    currentRange = Range.closed(userNumber + 1, currentRange.upperEndpoint());
                } else {
                    newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_TOO_LARGE);
                    currentRange = Range.closed(currentRange.lowerEndpoint(), userNumber - 1);
                }

                newAttempt.setNewRange(currentRange);
            }

            LOGGER.debug("newAttempt: {}", newAttempt);
            userAttempts.add(newAttempt);

        } while (targetIsNotHit);

        LOGGER.debug("...the target has been hit. The game is finished.");
        output.printMessageWithKey("generalMessages", "game.targetIsHit");
        output.printlnString(viewGenerator.getGameStatisticsView(userAttempts));

    }

    private int getNewNumberFromUser() {
        int userNumber;
        String enterNextNumberMessage = MessageFormat.format(
                output.getMessageWithKey("generalMessages", "menu.enterNextNumber"), currentRange);

        do {
            output.printString(enterNextNumberMessage);
            userNumber = input.readIntValue();
            LOGGER.debug("userNumber = {}", userNumber);
            if(userEnteredIncorrectValue(userNumber)) {
                output.printMessageWithKey("generalMessages", "input.enterNextNumber.error");
            }

        } while (userEnteredIncorrectValue(userNumber));

        return userNumber;
    }

    private boolean userEnteredIncorrectValue(int userNumber) {
        return !currentRange.contains(userNumber);
    }

    /*
    * Generates a random integer from the range [min;max] using an equality:
    * random[min;max] = random[0;max-min] + min;
    * Because the upper limit should be inclusive and standard nexInt() uses an exclusive one
    * we use '+1'.
    */
    private int randomInt(int min, int max) {
        return (ThreadLocalRandom.current().nextInt(max - min + 1) + min);
    }

    private int randomInt() {
        return randomInt(randomMinDefault, randomMaxDefault);
    }

    public static void setRandomMaxDefault(int newValue) {
        if(newValue < RANDOM_MAX_LOW_LIMIT || newValue > RANDOM_MAX_HIGH_LIMIT)
            throw new IllegalArgumentException();
        randomMaxDefault = newValue;
    }

    public static int getRandomMinDefault() {
        return randomMinDefault;
    }

    public static int getRandomMaxDefault() {
        return randomMaxDefault;
    }
}
