package com.stolser.javatraining.block05.reflection.model.vehicle;

import com.stolser.javatraining.block05.reflection.controller.Invocable;
import com.stolser.javatraining.block05.reflection.controller.NotNegative;
import com.stolser.javatraining.block05.reflection.model.Describable;
import com.stolser.javatraining.block05.reflection.model.TrafficParticipant;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.TransmissionType.MANUAL;

@TrafficParticipant
public class Car implements Vehicle, Motorizable, Describable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);
    private static final AtomicInteger nextUID = new AtomicInteger(1);
    private static final int CYLINDER_NUMBER_DEFAULT = 4;
    private static final int POWER_DEFAULT = 320;
    private static final double ACCELERATION_POWER = 0.01;
    private static final double MILLIS_TO_SECONDS = 0.001;
    private static final int BRAKES_EFFICIENCY = 50;
    private static final TransmissionType TRANS_TYPE_DEFAULT = MANUAL;
    private static final String ACCELERATING_TEXT = "Accelerating from %.2f to %.2f\n";
    private static final String BRAKING_TEXT = "Braking from %.2f to %.2f\n";
    private static final String MOVING_LEFT_TEXT = "Moving left at %.2f meters.\n";
    private static final String MOVING_RIGHT_TEXT = "Moving right at %.2f meters.\n";
    private static final String MAX_SPEED_TEXT = "The max speed = %.2f\n";
    private static final String CURRENT_SPEED_TEXT = "The current speed = %.2f\n";
    private static final String TO_STRING_PATTERN = "Car {'%s'; cylinders = %d; power = %d hp}";

    private int uid; // unique identifier
    @NotNull
    private String brand;
    private String description;
    @NotNegative
    private int cylinderNumber;
    @NotNegative
    private int power; // measured in 'hp' (horsepower);
    @NotNull
    private TransmissionType transType;
    @NotNegative
    private double currentSpeed;
    @NotNegative
    private double maxSpeed;
    private boolean isEngineOn;

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

    protected double setMaxSpeed() {
        double maxSpeed = (power * 0.7) * (1 + cylinderNumber/10);
        LOGGER.debug("maxSpeed = {}", maxSpeed);

        return maxSpeed;
    }

    public enum TransmissionType {
        MANUAL, AUTOMATIC, SEMI_AUTOMATIC, CVT;
    }

    @Override
    public int getUid() {
        return uid;
    }

    /**
     * @param time the duration in milliseconds of the car acceleration. The longer time, the higher speed
     *             of the car after calling this method.
     */

    @Override
    @Invocable(times = 3)
    public void accelerate(double time) {
        checkArgument(time > 0);

        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = Math.min(maxSpeed, currentSpeed +
                ((power * ACCELERATION_POWER) * cylinderNumber * (time * MILLIS_TO_SECONDS)));
        currentSpeed = newCurrentSpeed;

        System.out.printf(ACCELERATING_TEXT, oldCurrentSpeed, newCurrentSpeed);
    }

    /**
     * @param time the duration in milliseconds of the car braking. The longer time, the smaller speed
     *             of the car after calling this method.
     */
    @Override
    @Invocable(times = 2)
    public void brake(double time) {
        checkArgument(time > 0);

        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = Math.max(0, currentSpeed - (BRAKES_EFFICIENCY * (time * MILLIS_TO_SECONDS)));
        currentSpeed = newCurrentSpeed;

        System.out.printf(BRAKING_TEXT, oldCurrentSpeed, newCurrentSpeed);
    }

    @Override
    @Invocable(times = 0)
    public void moveLeft(double distance) {
        checkArgument(distance > 0);

        System.out.printf(MOVING_LEFT_TEXT, distance);
    }

    @Override
    @Invocable
    public void moveRight(double distance) {
        checkArgument(distance > 0);

        System.out.printf(MOVING_RIGHT_TEXT, distance);
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public int getCylinderNumber() {
        return cylinderNumber;
    }

    @Override
    public void setCylinderNumber(int cylinderNumber) {
        checkArgument(cylinderNumber > 0);

        this.cylinderNumber = cylinderNumber;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int power) {
        checkArgument(power > 0);

        this.power = power;
    }

    @Override
    @Invocable(isActive = true)
    public double getMaxSpeed() {
        System.out.printf(MAX_SPEED_TEXT, maxSpeed);

        return maxSpeed;
    }

    @Override
    @Invocable
    public double getCurrentSpeed() {
        System.out.printf(CURRENT_SPEED_TEXT, currentSpeed);

        return currentSpeed;
    }

    @Override
    public TransmissionType getTransType() {
        return transType;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_PATTERN, brand, cylinderNumber, power);
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
}
