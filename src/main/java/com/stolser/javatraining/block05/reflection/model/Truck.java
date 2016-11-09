package com.stolser.javatraining.block05.reflection.model;

import static com.google.common.base.Preconditions.checkArgument;

public class Truck extends Car implements Loadable {
    private double loadRating;
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
    public void loadCargo(double weight) {
        if (weight <= loadRating) {
            System.out.printf("Loading cargo (weight = %f)\n", weight);
            currentCargoWeight = weight;
        } else {
            System.out.printf("Sorry, the weight is too heavy!\n");
        }
    }

    @Override
    public void unLoadCargo() {
        System.out.printf("Unloading the cargo.");
        currentCargoWeight = 0.0;
    }
}
