package com.stolser.javatraining.block05.reflection.controller.proxy;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static Object getInvokable(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new InvokableHandler(object));
    }

    public static Object getImmutable(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new ImmutableHandler(object));
    }

    public static Object getNotNegative(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new NotNegativeHandler(object));
    }
}
