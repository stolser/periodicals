package com.stolser.javatraining.block05.reflection.controller.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Is used to create proxies that provide immutability for objects.
 * */
public class ImmutableHandler implements InvocationHandler {
    private Object proxied;

    public ImmutableHandler(Object proxied) {
        this.proxied = proxied;
    }

    /**
     * Prohibits calling setters (methods which names start with "set") on a proxied object by throwing
     * a runtime {@code ImmutabilityException}. All other methods just forwards to the proxied object.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().startsWith("set")) {
            throw new ImmutabilityException(method);
        }

        return method.invoke(proxied, args);
    }
}
