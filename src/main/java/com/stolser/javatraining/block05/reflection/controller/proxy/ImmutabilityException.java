package com.stolser.javatraining.block05.reflection.controller.proxy;

import java.lang.reflect.Method;

/**
 * Is thrown when a client calls a setter on a proxy object that is supposed to be immutable.
 */
public class ImmutabilityException extends RuntimeException {
    private Method invokedMethod;

    public ImmutabilityException(Method invokedMethod) {
        super(String.format("Trying to invoke a setter (%s) on an immutable object.",
                invokedMethod.getName()));
        this.invokedMethod = invokedMethod;
    }

    public ImmutabilityException(String message, Method invokedMethod) {
        super(message);
        this.invokedMethod = invokedMethod;
    }

    public Method getInvokedMethod() {
        return invokedMethod;
    }
}
