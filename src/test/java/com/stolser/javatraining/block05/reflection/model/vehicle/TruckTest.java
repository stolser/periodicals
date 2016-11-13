package com.stolser.javatraining.block05.reflection.model.vehicle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TruckTest {
    private static final String BRAND = "MAN";
    private static final int LOAD_RATING = 5000;
    private static final double DELTA = 0.000001;
    private static final int CARGO_WEIGHT_DELTA = 20;

    private Truck truck;

    @Before
    public void setUp() throws Exception {
        truck = new Truck(BRAND, LOAD_RATING);
    }

    @Test
    public void tryToLoadCargoShouldExecuteOkWhenWeightLessThanLoadRating() throws Exception {
        double cargoWeight = truck.getLoadRating() - CARGO_WEIGHT_DELTA;
        truck.tryToLoadCargo(cargoWeight);

        assertEquals(cargoWeight, truck.getCurrentCargoWeight(), DELTA);
    }

    @Test(expected = TooHeavyCargo.class)
    public void tryToLoadCargoShouldThrowExceptionWhenWeightMoreThanLoadRating() throws Exception {
        double cargoWeight = truck.getLoadRating() + CARGO_WEIGHT_DELTA;
        truck.tryToLoadCargo(cargoWeight);
    }

    @Test
    public void unLoadCargoShouldMakeCurrentCargoWeightEqualsZero() throws Exception {
        double cargoWeight = truck.getLoadRating() - CARGO_WEIGHT_DELTA;
        truck.tryToLoadCargo(cargoWeight);
        truck.unLoadCargo();

        assertEquals(0, truck.getCurrentCargoWeight(), DELTA);
    }
}