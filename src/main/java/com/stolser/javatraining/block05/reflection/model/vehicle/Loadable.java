package com.stolser.javatraining.block05.reflection.model.vehicle;

public interface Loadable {
    void loadCargo(double weight);
    void unLoadCargo();
    double getCurrentCargoWeight();
}
