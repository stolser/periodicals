package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;
import org.junit.Assert;
import org.junit.Test;

public class MoreLessGameControllerTest {

    @Test
    public void setRandomMaxDefaultWithCorrectValueShouldChangeIt() {
        int newValue = 250;
        MoreLessGame.setUpperBoundDefault(newValue);

        Assert.assertEquals(newValue, (int) MoreLessGame.getUpperBoundLimits().upperEndpoint());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBoundShouldThrowExceptionIfArgumentLessThanMin() {
        Range<Integer> lowerBoundRange = MoreLessGame.getLowerBoundLimits();

        MoreLessGame.setLowerBoundDefault(lowerBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBoundShouldThrowExceptionIfArgumentMoreThanMax() {
        Range<Integer> lowerBoundRange = MoreLessGame.getLowerBoundLimits();

        MoreLessGame.setLowerBoundDefault(lowerBoundRange.upperEndpoint() + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBoundShouldThrowExceptionIfArgumentLessThanMin() {
        Range<Integer> upperBoundRange = MoreLessGame.getUpperBoundLimits();

        MoreLessGame.setUpperBoundDefault(upperBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBoundShouldThrowExceptionIfArgumentMoreThanMax() {
        Range<Integer> upperBoundRange = MoreLessGame.getUpperBoundLimits();

        MoreLessGame.setUpperBoundDefault(upperBoundRange.upperEndpoint() + 1);
    }
}