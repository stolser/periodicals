package com.stolser.javatraining.block05.reflection.model.vehicle;

import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.*;

/**
 * Represents any kind of transportation means that have an engine. Contains methods for getting and
 * setting engine related information.
 */
public interface Motorizable {
    int getCylinderNumber();
    void setCylinderNumber(int cylinderNumber);
    int getPower();
    void setPower(int power);
    TransmissionType getTransType();
}
