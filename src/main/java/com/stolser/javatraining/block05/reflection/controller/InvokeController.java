package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import com.stolser.javatraining.view.ViewPrinter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.stolser.javatraining.controller.utils.ReflectionUtils.getShortNameAsString;

class InvokeController {
    private ViewPrinter output;

    InvokeController(ViewPrinter output) {
        this.output = output;
    }


    void invokeMethodsOf(Vehicle vehicle1) {
        Method[] methods = vehicle1.getClass().getDeclaredMethods();

        for (Method method : methods) {
            Invocable invocable = method.getAnnotation(Invocable.class);
            if ((invocable != null) && (invocable.isActive())) {
                String methodName = getShortNameAsString(method.getName());
                try {
                    for (int i = 1; i <= invocable.times(); i++) {
                        switch (methodName) {
                            case "accelerate":
                            case "brake":
                                double time = 1500;
                                method.invoke(vehicle1, time);
                                break;
                            case "moveLeft":
                            case "moveRight":
                                double distance = 10;
                                method.invoke(vehicle1, distance);
                        }
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
