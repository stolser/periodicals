package com.stolser.javatraining.block05.reflection.model;

public interface Vehicle {
    void startEngine();
    void accelerate(double time);
    void brake(double time);
    void moveLeft(double distance);
    void moveRight(double distance);
}
