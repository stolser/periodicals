package com.stolser.javatraining.block05.reflection.model.vehicle;

import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.*;

public interface Motorizable {
    int getCylinderNumber();
    void setCylinderNumber(int cylinderNumber);
    int getPower();
    void setPower(int power);
    TransmissionType getTransType();
}
