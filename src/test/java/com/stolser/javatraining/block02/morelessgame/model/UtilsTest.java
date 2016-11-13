package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.controller.utils.NumberUtils;
import org.junit.Test;

public class UtilsTest {
    @Test(expected = IllegalArgumentException.class)
    public void randomIntWithMinArgGraterThanMaxArgShouldThrowException() {
        int min = 100;
        int max = 50;

        NumberUtils.randomInt(min, max);
    }
}
