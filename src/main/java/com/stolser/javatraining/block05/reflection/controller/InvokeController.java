package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import com.stolser.javatraining.view.ViewPrinter;

import java.lang.reflect.Method;
import java.util.Arrays;

class InvokeController {
    private ViewPrinter output;

    InvokeController(ViewPrinter output) {
        this.output = output;
    }


    void invokeMethodsViaReflection(Vehicle vehicle1) {
        Method[] methods = vehicle1.getClass().getDeclaredMethods();

        Arrays.stream(methods)
                .forEach(method -> {
//                    String methodName =
                });
    }
}
