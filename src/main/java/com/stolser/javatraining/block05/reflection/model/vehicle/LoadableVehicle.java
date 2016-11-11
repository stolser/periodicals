package com.stolser.javatraining.block05.reflection.model.vehicle;

public interface LoadableVehicle extends Vehicle {
    /**
     * Tries to load cargo with the specified weight on this vehicle.
     */
    void loadCargo(double weight);

    /**
     * Unloads the cargo from this vehicle making its weight equals 0.
     */
    void unLoadCargo();
    double getCurrentCargoWeight();
}
