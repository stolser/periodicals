package com.stolser.javatraining.block05.reflection.model.vehicle;

import com.stolser.javatraining.block05.reflection.controller.Invokable;
import com.stolser.javatraining.block05.reflection.controller.NotNegative;
import com.stolser.javatraining.block05.reflection.model.UniquelyDescribable;
import com.stolser.javatraining.block05.reflection.model.TrafficParticipant;
import com.stolser.javatraining.controller.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.TransmissionType.MANUAL;

/**
 * A car entity having characteristics common for all vehicles.<br />
 *
 * @see com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle
 * @see Motorizeable
 * @see com.stolser.javatraining.block05.reflection.model.UniquelyDescribable
 */
@TrafficParticipant
public class Car implements Vehicle, Motorizeable, UniquelyDescribable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);
    private static final AtomicInteger nextUID = new AtomicInteger(1);
    private static final int CYLINDER_NUMBER_DEFAULT = 4;
    private static final int POWER_DEFAULT = 320;
    /**
     * Participates in calculating the current speed during accelerating
     * the power is multiplied by this ratio).
     */
    private static final double ACCELERATION_RATIO = 0.01;
    private static final double MILLIS_TO_SECONDS_RATIO = 0.001;
    /**
     * Participates in calculating the current speed during braking. Measured in
     * number of kilometers per second.
     */
    private static final int BRAKES_EFFICIENCY_RATIO = 50;
    private static final TransmissionType TRANS_TYPE_DEFAULT = MANUAL;
    public static final double MAX_SPEED_POWER_RATIO = 0.7;
    public static final int MAX_SPEED_CYLINDER_RATIO = 10;

    /**
     * Unique identifier of this car.
     */
    private int uid;
    private String brand;
    private String description;
    @NotNegative
    private int cylinderNumber;
    /**
     * The power measured in horse-powers ('hp').
     */
    @NotNegative
    private int power;
    private TransmissionType transType;
    @NotNegative
    private double currentSpeed;
    @NotNegative
    private double maxSpeed;

    public Car(String brand) {
        this(brand, CYLINDER_NUMBER_DEFAULT, POWER_DEFAULT, TRANS_TYPE_DEFAULT);
    }

    public Car(String brand, int cylinderNumber, int power, TransmissionType transType) {
        checkNotNull(brand);
//        checkArgument(cylinderNumber > 0);  // is deliberately commented. See ReflectionController.start().
        checkArgument(power > 0);
        checkNotNull(transType);

        this.uid = nextUID.getAndIncrement();
        this.brand = brand;
        this.cylinderNumber = cylinderNumber;
        this.power = power;
        this.transType = transType;
        maxSpeed = setMaxSpeed();
    }

    /**
     * Sets the maximum speed of this car taking into account some technical characteristics.
     * @return
     */
    protected double setMaxSpeed() {
        double maxSpeed = (power * MAX_SPEED_POWER_RATIO) * (1 + cylinderNumber/ MAX_SPEED_CYLINDER_RATIO);
        LOGGER.debug("maxSpeed = {}", maxSpeed);

        return maxSpeed;
    }

    /**
     * Represents a type of the gear box.
     */
    public enum TransmissionType {
        MANUAL, AUTOMATIC, SEMI_AUTOMATIC, CVT;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    @Invokable(times = 3)
    public void accelerate(double time) {
        checkArgument(time > 0);

        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = getAcceleratedSpeed(time);
        currentSpeed = newCurrentSpeed;

        System.out.printf("Accelerating from %.2f to %.2f\n", oldCurrentSpeed, newCurrentSpeed);
    }

    private double getAcceleratedSpeed(double time) {
        return Math.min(maxSpeed, currentSpeed +
                ((power * ACCELERATION_RATIO) * cylinderNumber * (time * MILLIS_TO_SECONDS_RATIO)));
    }

    @Override
    @Invokable(times = 2)
    public void brake(double time) {
        checkArgument(time > 0);

        double oldCurrentSpeed = currentSpeed;
        double newCurrentSpeed = getSlowedDownSpeed(time);
        currentSpeed = newCurrentSpeed;

        System.out.printf("Braking from %.2f to %.2f\n", oldCurrentSpeed, newCurrentSpeed);
    }

    private double getSlowedDownSpeed(double time) {
        return Math.max(0, currentSpeed -
                (BRAKES_EFFICIENCY_RATIO * (time * MILLIS_TO_SECONDS_RATIO)));
    }

    @Override
    @Invokable(times = 0)
    public void moveLeft(double distance) {
        checkArgument(distance > 0);

        System.out.printf("Moving left at %.2f meters.\n", distance);
    }

    @Override
    @Invokable
    public void moveRight(double distance) {
        checkArgument(distance > 0);

        System.out.printf("Moving right at %.2f meters.\n", distance);
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
    @Invokable(isActive = true)
    public double getMaxSpeed() {
        System.out.printf("The max speed = %.2f\n", maxSpeed);

        return maxSpeed;
    }

    @Override
    @Invokable
    public double getCurrentSpeed() {
        System.out.printf("The current speed = %.2f\n", currentSpeed);

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
        return String.format("%s {'%s'; cylinders = %d; power = %d hp}",
                ReflectionUtils.getShortNameAsString(this.getClass().getName()),
                brand, cylinderNumber, power);
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
