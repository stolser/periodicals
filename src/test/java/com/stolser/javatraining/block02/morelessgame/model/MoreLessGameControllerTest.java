package com.stolser.javatraining.block02.morelessgame.model;

import com.google.common.collect.Range;
import com.stolser.javatraining.block02.morelessgame.model.game.MoreLessGame;
import org.junit.Test;

public class MoreLessGameControllerTest {

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBound_Should_ThrowException_IfArgumentLessThanMin() {
        Range<Integer> lowerBoundRange = MoreLessGame.getLowerBoundLimits();

        MoreLessGame.setLowerBoundDefault(lowerBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLowerBound_Should_ThrowException_IfArgumentMoreThanMax() {
        Range<Integer> lowerBoundRange = MoreLessGame.getLowerBoundLimits();

        MoreLessGame.setLowerBoundDefault(lowerBoundRange.upperEndpoint() + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBound_Should_ThrowException_IfArgumentLessThanMin() {
        Range<Integer> upperBoundRange = MoreLessGame.getUpperBoundLimits();

        MoreLessGame.setUpperBoundDefault(upperBoundRange.lowerEndpoint() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpperBound_Should_ThrowException_IfArgumentMoreThanMax() {
        Range<Integer> upperBoundRange = MoreLessGame.getUpperBoundLimits();

        MoreLessGame.setUpperBoundDefault(upperBoundRange.upperEndpoint() + 1);
    }
}