package com.stolser.javatraining.block05.reflection.model.vehicle;

import org.junit.Before;
import org.junit.Test;

import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.TransmissionType.AUTOMATIC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTest {
    private static final String BRAND = "BMW";
    private static final int CYLINDER_NUMBER = 5;
    private static final int POWER = 420;
    private static final Car.TransmissionType TRANS_TYPE = AUTOMATIC;
    private static final double DELTA = 0.000001;
    private static final int ACCELERATION_TIME = 100_000;

    private Car car;

    @Before
    public void setUp() {
        car = new Car(BRAND, CYLINDER_NUMBER, POWER, TRANS_TYPE);
    }

    @Test
    public void carShouldHaveCorrectInitialState() {
        assertEquals(BRAND, car.getBrand());
        assertEquals(CYLINDER_NUMBER, car.getCylinderNumber());
        assertEquals(POWER, car.getPower());
        assertEquals(TRANS_TYPE, car.getTransType());
        assertEquals(0, car.getCurrentSpeed(), DELTA);
        assertTrue(car.getMaxSpeed() > 0);
    }

    @Test
    public void accelerateShouldNotMakeCurrentSpeedMoreThanMaxSpeed() throws Exception {
        assertEquals(0, car.getCurrentSpeed(), DELTA);

        car.accelerate(ACCELERATION_TIME);
        assertTrue(car.getMaxSpeed() >= car.getCurrentSpeed());

    }

    @Test
    public void brakeShouldNotMakeCurrentSpeedLessThanZero() throws Exception {
        car.accelerate(ACCELERATION_TIME);
        assertTrue(car.getCurrentSpeed() > 0.0);

        car.brake(ACCELERATION_TIME * 5);
        assertEquals(car.getCurrentSpeed(), 0.0, DELTA);
    }
}