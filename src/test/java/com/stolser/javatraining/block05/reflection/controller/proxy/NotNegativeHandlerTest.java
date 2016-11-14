package com.stolser.javatraining.block05.reflection.controller.proxy;

import com.stolser.javatraining.block05.reflection.controller.NotNegative;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotNegativeHandlerTest {
    private OperationsOnFields notNegative;

    @Before
    public void setUp() {
        OperationsOnFields object = newInstance();
        notNegative = (OperationsOnFields) ProxyFactory.newNotNegativeProxyFor(object);
    }

    @Test
    public void fieldAnnotatedNotNegative_Should_BeAssignablePositiveValues() {
        assertEquals(0, notNegative.getResult());

        notNegative.increaseBy(2);
        assertEquals(2, notNegative.getResult());
    }

    @Test(expected = IllegalStateException.class)
    public void notNegativeProxy_Should_ThrowException_IfNotNegativeField_AssignedNegativeValue() {
        assertEquals(0, notNegative.getResult());

        notNegative.decreaseBy(2);
    }

    interface OperationsOnFields {
        void increaseBy(int value);
        void decreaseBy(int value);
        int getResult();
    }

    private OperationsOnFields newInstance() {
        return new OperationsOnFields() {
            @NotNegative
            private int result;

            @Override
            public void increaseBy(int value) {
                result += value;
            }

            @Override
            public void decreaseBy(int value) {
                result -= value;
            }

            @Override
            public int getResult() {
                return result;
            }
        };
    }
}