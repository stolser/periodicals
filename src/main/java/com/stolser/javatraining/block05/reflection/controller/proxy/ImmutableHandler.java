package com.stolser.javatraining.block05.reflection.controller.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ImmutableHandler implements InvocationHandler {
    private Object proxied;

    public ImmutableHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().startsWith("set")) {
            throw new ImmutabilityException(method);
        }

        return method.invoke(proxied, args);
    }
}
