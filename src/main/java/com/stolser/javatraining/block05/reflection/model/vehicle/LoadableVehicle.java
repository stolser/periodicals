package com.stolser.javatraining.block05.reflection.model.vehicle;

/**
 * A type of vehicles that can transport cargo.
 */
public interface LoadableVehicle extends Vehicle {
    /**
     * Tries to load cargo with the specified weight on this vehicle. Throws a {@link TooHeavyCargo}
     * if weight exceeds the load rating.
     */
    void tryToLoadCargo(double weight);

    /**
     * Unloads the cargo from this vehicle making its weight equals 0.
     */
    void unLoadCargo();

    /**
     * @return the weight of the cargo already loaded on this vehicle
     */
    double getCurrentCargoWeight();
    double getLoadRating();
}
