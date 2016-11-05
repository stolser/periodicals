package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    @Test
    public void setRandomMaxDefaultWithCorrectValueShouldChangeIt() {
        int newValue = 250;
        Game.setUpperBoundDefault(newValue);

        Assert.assertEquals(newValue, Game.getUpperBoundDefault());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBoundShouldThrowExceptionIfArgumentLessThanMin() {
        Range<Integer> lowerBoundRange = Game.getLowerBoundLimits();

        Game.setLowerBoundDefault(lowerBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBoundShouldThrowExceptionIfArgumentMoreThanMax() {
        Range<Integer> lowerBoundRange = Game.getLowerBoundLimits();

        Game.setLowerBoundDefault(lowerBoundRange.upperEndpoint() + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBoundShouldThrowExceptionIfArgumentLessThanMin() {
        Range<Integer> upperBoundRange = Game.getUpperBoundLimits();

        Game.setUpperBoundDefault(upperBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBoundShouldThrowExceptionIfArgumentMoreThanMax() {
        Range<Integer> upperBoundRange = Game.getUpperBoundLimits();

        Game.setUpperBoundDefault(upperBoundRange.upperEndpoint() + 1);
    }
}