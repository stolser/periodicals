package com.stolser.javatraining.block05.reflection.controller.proxy;

import java.lang.reflect.Proxy;

/**
 * A utility class containing exclusively static methods for creating proxy objects.
 */
public class ProxyFactory {
    public static Object newInvokableProxyFor(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new InvokableHandler(object));
    }

    public static Object newImmutableProxyFor(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new ImmutableHandler(object));
    }

    public static Object newNotNegativeProxyFor(Object object) {
        Class clazz = object.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = clazz.getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, new NotNegativeHandler(object));
    }
}
