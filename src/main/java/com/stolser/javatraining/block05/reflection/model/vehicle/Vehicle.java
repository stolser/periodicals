package com.stolser.javatraining.block05.reflection.model.vehicle;

/**
 * Represents any kind of transportation means that can move and respectively <br />
 * can have the maximum and current speed.
 */
public interface Vehicle {
    /**
     * Increases a current speed of the vehicle unless it is already the max speed.
     * @param time duration in milliseconds of acceleration. The longer time, the higher speed
     *             of the vehicle after applying this method
     */
    void accelerate(double time);

    /**
     * Decreases a current spped of the vehicle unless it is already zero.
     * @param time duration in milliseconds of braking. The longer time, the lesser speed
     *             of the vehicle after applying this method
     */
    void brake(double time);

    /**
     * Moves the vehicle to the left at the specified distance. A vehicle can move regardless of <br />
     * its current speed.
     * @param distance specifies how far the vehicle will move
     */
    void moveLeft(double distance);

    /**
     * Moves the vehicle to the right at the specified distance. A vehicle can move regardless of <br />
     * its current speed.
     * @param distance specifies how far the vehicle will move
     */
    void moveRight(double distance);
    double getMaxSpeed();
    double getCurrentSpeed();
}
