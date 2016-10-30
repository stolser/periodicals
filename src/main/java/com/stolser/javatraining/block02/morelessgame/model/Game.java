package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.base.Preconditions;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.menu.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    public static final int RANDOM_MAX_LOW_LIMIT = 10;
    public static final int RANDOM_MAX_HIGH_LIMIT = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private ViewPrinter output;
    private InputReader input;
    private static int randomMinDefault = 0;
    private static int randomMaxDefault = 100;
    private int nextAttemptSerialNo = 1;
    private List<UserAttempt> userAttempts;
    private int[] currentRange;
    private int target;
    private boolean targetIsNotHit;

    public Game(Environment environment) {
        output = environment.getViewPrinter();
        input = environment.getInputReader();
        target = randomInt();
        userAttempts = new LinkedList<>();
        currentRange = new int[]{randomMinDefault, randomMaxDefault};
        targetIsNotHit = true;
    }

    public enum AttemptResult {
        ATTEMPT_RESULT_TOO_SMALL("too small"),
        ATTEMPT_RESULT_TOO_LARGE("too large"),
        ATTEMPT_RESULT_SCORE("score!");

        private String description;

        AttemptResult(String description) {
            this.description = description;
        }

        public String description() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public void start() {
        LOGGER.debug("Starting a new Game. Default range: [{}, {}]...",
                currentRange[0], currentRange[1]);

        do {
            int userNumber = getNewNumberFromUser();
            UserAttempt newAttempt = new UserAttempt(nextAttemptSerialNo,
                    new int[]{currentRange[0],currentRange[1]});
            nextAttemptSerialNo++;
            newAttempt.setNumber(userNumber);

            if (userNumber == target) {
                newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_SCORE);
                targetIsNotHit = false;
            } else {
                if(userNumber < target) {
                    currentRange[0] = userNumber + 1;
                    newAttempt.setNewRange(new int[]{currentRange[0],currentRange[1]});
                    newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_TOO_SMALL);
                } else {
                    currentRange[1] = userNumber - 1;
                    newAttempt.setNewRange(new int[]{currentRange[0],currentRange[1]});
                    newAttempt.setResult(AttemptResult.ATTEMPT_RESULT_TOO_LARGE);
                }
            }

            LOGGER.debug("newAttempt: {}", newAttempt);

        } while (targetIsNotHit);

        LOGGER.debug("...the target has been hit. The game is finished.");

    }

    private int getNewNumberFromUser() {
        int userNumber;
        String enterNextNumberMessage = MessageFormat.format(
                output.getMessageWithKey("generalMessages", "menu.enterNextNumber"),
                currentRange[0], currentRange[1]);

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
        return (userNumber < currentRange[0] || userNumber > currentRange[1]);
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
