package com.stolser.javatraining.block05.reflection.controller.proxy;

import com.stolser.javatraining.block05.reflection.controller.Invokable;
import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvokableHandlerTest {
    private static final double DELTA = 0.000001;

    private Vehicle invokableProxy;

    @Before
    public void setUp() {
        Vehicle invokable = newVehicle();
        invokableProxy = (Vehicle) ProxyFactory.newInvokableProxyFor(invokable);
    }

    @Test
    public void invokableProxy_Should_CallMethodsAnnotated_Invokable_SpecifiedNumberOfTimes() {
        assertEquals(0, invokableProxy.getCurrentSpeed(), DELTA);

        invokableProxy.accelerate(0);
        assertEquals(4, invokableProxy.getCurrentSpeed(), DELTA);

        invokableProxy.brake(0);
        assertEquals(2, invokableProxy.getCurrentSpeed(), DELTA);

        invokableProxy.moveRight(0);
        assertEquals(3, invokableProxy.getCurrentSpeed(), DELTA);

        invokableProxy.moveLeft(0);
        assertEquals(3, invokableProxy.getCurrentSpeed(), DELTA);

        invokableProxy.getMaxSpeed();
        assertEquals(3, invokableProxy.getCurrentSpeed(), DELTA);
    }

    private Vehicle newVehicle() {
        return new Vehicle() {
            private int result;

            @Invokable(times = 4)
            @Override
            public void accelerate(double time) {
                result++;
            }

            @Invokable(times = 2)
            @Override
            public void brake(double time) {
                result--;
            }

            @Invokable
            @Override
            public void moveRight(double distance) {
                result++;
            }

            @Invokable(isActive = false)
            @Override
            public void moveLeft(double distance) {
                result--;
            }

            @Invokable(times = -4)
            @Override
            public double getMaxSpeed() {
                result--;
                return 0;
            }

            @Override
            public double getCurrentSpeed() {
                return result;
            }
        };
    }
}