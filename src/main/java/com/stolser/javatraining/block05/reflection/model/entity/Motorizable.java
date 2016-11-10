package com.stolser.javatraining.block05.reflection.model.entity;

import static com.stolser.javatraining.block05.reflection.model.entity.Car.*;

public interface Motorizable {
    int getCylinderNumber();
    void setCylinderNumber(int cylinderNumber);
    int getPower();
    void setPower(int power);
    TransmissionType getTransType();
}
