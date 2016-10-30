package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Game;

import java.util.Arrays;

import static com.stolser.javatraining.block02.morelessgame.model.Game.*;

public class UserAttempt {
    private int serialNo;
    private int[] currentRange;
    private int[] newRange;
    private int number;
    private AttemptResult result;

    public UserAttempt(int serialNo, int[] currentRange) {
        this.serialNo = serialNo;
        this.currentRange = currentRange;
    }

    public void setNewRange(int[] newRange) {
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

    public int[] getCurrentRange() {
        return currentRange;
    }

    public int[] getNewRange() {
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
                serialNo, Arrays.toString(currentRange), number, result, Arrays.toString(newRange));
    }
}
