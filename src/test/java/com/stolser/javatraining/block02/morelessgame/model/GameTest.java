package com.stolser.javatraining.block02.morelessgame.model;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    @Test
    public void setRandomMaxDefaultWithCorrectValueShouldChangeIt() {
        int newValue = 250;
        Game.setUpperBoundDefault(newValue);

        Assert.assertEquals(newValue, Game.getUpperBoundDefault());
    }
}