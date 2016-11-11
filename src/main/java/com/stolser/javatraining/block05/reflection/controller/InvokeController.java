package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.stolser.javatraining.controller.utils.ReflectionUtils.getShortNameAsString;

/**
 * Contains methods for invoking methods via reflection.
 */
class InvokeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvokeController.class);
    private static final String ACCELERATE_METHOD = "accelerate";
    private static final String BRAKE_METHOD = "brake";
    private static final String MOVE_LEFT_METHOD = "moveLeft";
    private static final String MOVE_RIGHT_METHOD = "moveRight";

    private Invocable invocable;
    private Vehicle vehicle;
    private Method method;

    /**
     * Using the Reflection API invokes methods on a Vehicle object if it is annotated with<br />
     * {@code @Invocable}.
     */
    void invokeMethodsOf(Vehicle annotation) {
        this.vehicle = annotation;

        for (Method nextMethod : vehicle.getClass().getDeclaredMethods()) {
            invocable = nextMethod.getAnnotation(Invocable.class);
            method = nextMethod;
            callMethodIfInvocableIsActive();
        }
    }

    private void callMethodIfInvocableIsActive() {
        if ((invocable != null) && (invocable.isActive())) {
            String methodName = getShortNameAsString(method.getName());

            try {
                for (int i = 1; i <= invocable.times(); i++) {
                    callMethodOnce(methodName);
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.debug("Reflection exception during invoking {} method", method.getName(), e);
                throw new RuntimeException();
            }
        }
    }

    private void callMethodOnce(String methodName) throws IllegalAccessException, InvocationTargetException {
        switch (methodName) {
            case ACCELERATE_METHOD:
            case BRAKE_METHOD:
                double time = 1500;
                method.invoke(vehicle, time);
                break;
            case MOVE_LEFT_METHOD:
            case MOVE_RIGHT_METHOD:
                double distance = 10;
                method.invoke(vehicle, distance);
                break;
            default:
                method.invoke(vehicle);
        }
    }
}
