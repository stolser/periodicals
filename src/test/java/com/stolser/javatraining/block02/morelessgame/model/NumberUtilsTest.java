package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.controller.utils.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NumberUtilsTest {
    private int min;
    private int max;

    public NumberUtilsTest(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
                { 10, 50 },
                { 10500, 50777 },
                { -200, 450 },
                { -250, 0 },
                { -100, -15 }
        });
    }

    @Test
    public void randomInt_Should_ReturnValueFromRange() {
        int random = NumberUtils.randomInt(min, max);

        assertTrue(random >= min);
        assertTrue(random <= max);
    }

}