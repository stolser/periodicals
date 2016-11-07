package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.controller.Utils;
import org.junit.Test;

public class UtilsTest2 {
    @Test(expected = IllegalArgumentException.class)
    public void randomIntWithMinArgGraterThanMaxArgShouldThrowException() {
        int min = 100;
        int max = 50;

        Utils.randomInt(min, max);
    }
}
