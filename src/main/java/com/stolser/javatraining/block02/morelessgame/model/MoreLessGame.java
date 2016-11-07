package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;
import com.stolser.javatraining.controller.Utils;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class MoreLessGame {
    /**
     * The minimum distance between the lower and upper bound in the initial range.
     */
    private static final int MINIMUM_RANGE_SIZE = 10;

    /**
     * The smallest possible custom lowerBound. User cannot set the lowerBound
     * smaller than that number.
     */
    private static final int LOWER_BOUND_MIN = -1000;

    /**
     * The biggest possible custom upperBound. User cannot set the upperBound
     * bigger than that number.
     */
    private static final int UPPER_BOUND_MAX = 1000;

    /**
     * A default value for the Lower Bound of the range.
     */
    private static int lowerBoundDefault = 0;

    /**
     * A default value for the Upper Bound of the range.
     */
    private static int upperBoundDefault = 100;

    /**
     * An attempt counter for the current game.
     */
    private int nextAttemptSerialNo = 0;

    /**
     * All attempts of a user during this game.
     */
    private List<UserAttempt> userAttempts;

    /**
     * The secret number that the computer has picked.
     */
    private int secretNumber;

    /**
     * The current range in which the secret number resides.
     */
    private Range<Integer> currentRange;

    public MoreLessGame() {
        userAttempts = new LinkedList<>();
        secretNumber = Utils.randomInt(lowerBoundDefault, upperBoundDefault);
        currentRange = Range.closed(lowerBoundDefault, upperBoundDefault);
    }

    public void addNewUserAttempt(UserAttempt attempt) {
        userAttempts.add(attempt);
    }

    public boolean secretNumberEqualsTo(int value) {
        return (secretNumber == value);
    }

    public boolean secretNumberGraterThan(int value) {
        return (secretNumber > value);
    }

    public static Range<Integer> getLowerBoundLimits() {
        return Range.closed(LOWER_BOUND_MIN,
                upperBoundDefault - MINIMUM_RANGE_SIZE);
    }

    public static boolean isValueForLowerBoundOk(int newValue) {
        return getLowerBoundLimits().contains(newValue);
    }

    public static void setLowerBoundDefault(int newValue) {
        checkArgument(isValueForLowerBoundOk(newValue));
        lowerBoundDefault = newValue;
    }

    public static Range<Integer> getUpperBoundLimits() {
        return Range.closed(lowerBoundDefault + MINIMUM_RANGE_SIZE,
                UPPER_BOUND_MAX);
    }

    public static boolean isValueForUpperBoundOk(int newValue) {
        return getUpperBoundLimits().contains(newValue);
    }

    public static void setUpperBoundDefault(int newValue) {
        checkArgument(isValueForUpperBoundOk(newValue));
        upperBoundDefault = newValue;
    }

    public int generateAndGetAttemptSerialNo() {
        return ++nextAttemptSerialNo;
    }

    public List<UserAttempt> getUserAttempts() {
        return userAttempts;
    }

    public Range<Integer> getCurrentRange() {
        return currentRange;
    }

    public void setCurrentRange(Range<Integer> currentRange) {
        this.currentRange = currentRange;
    }
}
