package com.stolser.javatraining.block05.reflection.model.vehicle;

public interface Vehicle {
    void accelerate(double time);
    void brake(double time);
    void moveLeft(double distance);
    void moveRight(double distance);
    double getMaxSpeed();
    double getCurrentSpeed();
}
