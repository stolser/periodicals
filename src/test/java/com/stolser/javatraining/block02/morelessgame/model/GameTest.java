package com.stolser.javatraining.block02.morelessgame.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void setRandomMaxDefaultWithCorrectValueShouldChangeIt() {
        int newValue = 250;
        Game.setRandomMaxDefault(newValue);

        Assert.assertEquals(newValue, Game.getRandomMaxDefault());
    }
}