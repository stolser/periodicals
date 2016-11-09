package com.stolser.javatraining.block05.reflection.model;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.*;
import static com.stolser.javatraining.block05.reflection.model.Car.TransmissionType.*;

@TrafficParticipant
public class Car implements Vehicle {
    private static final AtomicInteger nextUID = new AtomicInteger(1);
    private static final int CYLINDER_NUMBER_DEFAULT = 4;
    private static final int POWER_DEFAULT = 320;
    private static final double ACCELERATION_POWER = 0.01;
    private static final double MILLIS_TO_SECONDS = 0.001;
    private static final int BRAKES_EFFICIENCY = 50;
    protected static final TransmissionType TRANS_TYPE_DEFAULT = MANUAL;

    private int uid; // unique identifier
    private String brand;
    private int cylinderNumber;
    private int power; // measured in 'hp' (horsepower);
    private TransmissionType transType;
    private double currentSpeed;
    private double maxSpeed;

    public Car(String brand) {
        this(brand, CYLINDER_NUMBER_DEFAULT, POWER_DEFAULT, TRANS_TYPE_DEFAULT);
    }

    public Car(String brand, int cylinderNumber, int power, TransmissionType transType) {
        checkNotNull(brand);
        checkArgument(cylinderNumber > 0);
        checkArgument(power > 0);
        checkNotNull(transType);

        this.uid = nextUID.getAndIncrement();
        this.brand = brand;
        this.cylinderNumber = cylinderNumber;
        this.power = power;
        this.transType = transType;
        maxSpeed = setMaxSpeed();
    }

    private double setMaxSpeed() {
        return (power * 0.7) * (1 + cylinderNumber/10);
    }

    public enum TransmissionType {
        MANUAL, AUTOMATIC, SEMI_AUTOMATIC, CVT;
    }

    /**
     * Starts the engine without moving.
     */
    @Override
    @Invocable
    public void startEngine() {
        System.out.println("Starting the engine.");
    }

    /**
     * @param time the duration in milliseconds of the car acceleration. The longer time, the higher speed
     *             of the car after calling this method.
     */

    @Override
    @Invocable(times = 2)
    public void accelerate(double time) {
        checkArgument(time > 0);
        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = Math.max(maxSpeed, currentSpeed +
                ((power * ACCELERATION_POWER) * cylinderNumber * (time * MILLIS_TO_SECONDS)));
        currentSpeed = newCurrentSpeed;
        System.out.printf("Accelerating from %f to %f\n", oldCurrentSpeed, newCurrentSpeed);
    }

    /**
     * @param time the duration in milliseconds of the car braking. The longer time, the smaller speed
     *             of the car after calling this method.
     */
    @Override
    @Invocable(times = 3)
    public void brake(double time) {
        checkArgument(time > 0);
        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = Math.min(0, currentSpeed - (BRAKES_EFFICIENCY * (time * MILLIS_TO_SECONDS)));
        currentSpeed = newCurrentSpeed;
        System.out.printf("Braking from %f to %f\n", oldCurrentSpeed, newCurrentSpeed);
    }

    @Override
    @Invocable
    public void moveLeft(double distance) {
        checkArgument(distance > 0);
        System.out.printf("Moving left at %f meters.\n", distance);
    }

    @Override
    @Invocable
    public void moveRight(double distance) {
        checkArgument(distance > 0);
        System.out.printf("Moving right at %f meters.\n", distance);
    }

    @Override
    public String toString() {
        return String.format("Car {'%s'; cylinders = %d; power = %d hp}", brand, cylinderNumber, power);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return uid == car.uid;
    }

    @Override
    public int hashCode() {
        return uid;
    }

    public String getBrand() {
        return brand;
    }

    public int getCylinderNumber() {
        return cylinderNumber;
    }

    public void setCylinderNumber(int cylinderNumber) {
        checkArgument(cylinderNumber > 0);
        this.cylinderNumber = cylinderNumber;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        checkArgument(power > 0);
        this.power = power;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public int getUid() {
        return uid;
    }

    public TransmissionType getTransType() {
        return transType;
    }
}
