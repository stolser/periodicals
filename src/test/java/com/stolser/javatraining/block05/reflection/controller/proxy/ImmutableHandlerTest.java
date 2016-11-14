package com.stolser.javatraining.block05.reflection.controller.proxy;

import com.stolser.javatraining.block05.reflection.model.vehicle.Car;
import com.stolser.javatraining.block05.reflection.model.vehicle.Motorizeable;
import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.TransmissionType.AUTOMATIC;

public class ImmutableHandlerTest {
    private Motorizeable immutable;

    @Before
    public void setup() {
        Vehicle vehicle1 = new Car("BMW", 5, 420, AUTOMATIC);
        immutable = (Motorizeable) ProxyFactory.newImmutableProxyFor(vehicle1);
    }

    @Test(expected = ImmutabilityException.class)
    public void settersOnImmutableProxy_Should_ThrowException() {
        immutable.setPower(350);
    }

    @Test
    public void gettersOnImmutableProxy_Should_ExecuteNormally() {
        immutable.getPower();
        immutable.getCylinderNumber();
        immutable.getTransType();
    }
}