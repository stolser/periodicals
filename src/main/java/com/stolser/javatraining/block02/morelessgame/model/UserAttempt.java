package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;

/**
 * Represents an abstraction of user attempt to guess a number picked by the computer.
 */
public class UserAttempt {
    /**
     * The number of this attempt to guess a number.
     */
    private int serialNo;
    /**
     * A resulted range after all previous attempts inside which the secret number resides.
     */
    private Range<Integer> currentRange;
    /**
     * A new range inside which the secret number reside after processing this attempt.
     * If user has guessed the number {@code newRange} is {@code null}.
     */
    private Range<Integer> newRange;
    /**
     * The number that user chose during this attempt.
     */
    private int number;
    /**
     * The result of this attempt.
     */
    private AttemptResult result;

    public UserAttempt(int serialNo, Range<Integer> currentRange) {
        this.serialNo = serialNo;
        this.currentRange = currentRange;
    }

    public enum AttemptResult {
        ATTEMPT_RESULT_TOO_SMALL("attemptResult.tooSmall"),
        ATTEMPT_RESULT_TOO_LARGE("attemptResult.tooLarge"),
        ATTEMPT_RESULT_SCORE("attemptResult.score");

        private String systemName;

        AttemptResult(String systemName) {
            this.systemName = systemName;
        }

        @Override
        public String toString() {
            return systemName;
        }
    }

    public void setNewRange(Range<Integer> newRange) {
        this.newRange = newRange;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setResult(AttemptResult result) {
        this.result = result;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public Range<Integer> getCurrentRange() {
        return currentRange;
    }

    public Range<Integer> getNewRange() {
        return newRange;
    }

    public int getNumber() {
        return number;
    }

    public AttemptResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("UserAttempt{serialNo = %d, currentRange = %s, " +
                "number = %d, result = '%s', newRange = %s}",
                serialNo, currentRange, number, result, newRange);
    }
}
