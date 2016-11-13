package com.stolser.javatraining.block05.reflection.model.vehicle;

import com.stolser.javatraining.block05.reflection.controller.NotNegative;

import static com.google.common.base.Preconditions.checkArgument;

public class Truck extends Car implements LoadableVehicle {
    private static final String UNLOADING_CARGO_TEXT = "Unloading the cargo.\n";
    @NotNegative
    private double loadRating;
    @NotNegative
    private double currentCargoWeight;

    /**
     * @param brand the name of the brand
     * @param loadRating the maximum weight in kg that can be loaded on this truck
     */
    public Truck(String brand, double loadRating) {
        super(brand);

        checkArgument(loadRating > 0);
        this.loadRating = loadRating;
    }

    public Truck(String brand, int cylinderNumber, int power, TransmissionType transType, double loadRating) {
        super(brand, cylinderNumber, power, transType);

        checkArgument(loadRating > 0);
        this.loadRating = loadRating;
    }

    @Override
    public void tryToLoadCargo(double weight) {
        if (weight <= loadRating) {
            System.out.printf("Loading cargo (weight = %f)\n", weight);
            currentCargoWeight = weight;
        } else {
            throw new TooHeavyCargo();
        }
    }

    @Override
    public void unLoadCargo() {
        System.out.printf(UNLOADING_CARGO_TEXT);
        currentCargoWeight = 0.0;
    }

    @Override
    public double getCurrentCargoWeight() {
        return currentCargoWeight;
    }

    @Override
    public double getLoadRating() {
        return loadRating;
    }
}
