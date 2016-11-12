package com.stolser.javatraining.block05.reflection.controller.proxy;

import com.stolser.javatraining.block05.reflection.controller.Invokable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class InvokableHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvokableHandler.class);
    private static final String METHOD_SEPARATOR = "..................";
    private Object proxied;

    public InvokableHandler(Object proxied) {
        this.proxied = proxied;
    }

    /**
     * Using the Reflection API checks for the {@code Invokable} annotation and processes it
     * according to its logic.
     * @see Invokable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Method proxiedMethod = proxied.getClass().getMethod(method.getName(),
                method.getParameterTypes());

        if (proxiedMethod.isAnnotationPresent(Invokable.class)) {
            Invokable invokable = proxiedMethod.getAnnotation(Invokable.class);
            int times = invokable.times();

            displayInvokingMessage(method, times);

            if (invokable.isActive() && (times > 0)) {
                try {
                    for (int i = 1; i <= times; i++) {
                       result = method.invoke(proxied, args);
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.debug("Reflection exception during invoking '{}' method", method.getName(), e);
                    throw new RuntimeException();
                }
            } else {
                Class returnType = method.getReturnType();
                if (returnType.isPrimitive()) {
                    result = getDefaultReturnValue(returnType);
                }
            }

            System.out.println(METHOD_SEPARATOR);
        } else {
            result = method.invoke(proxied, args);
        }

        LOGGER.debug("handler.invoke() returns '{}'", result);

        return result;
    }

    private void displayInvokingMessage(Method method, int times) {
        String multipleForm = (times == 1) ? "time" : "times";

        System.out.printf("Invoking %s() %d %s:\n", method.getName(), times, multipleForm);
    }

    private Object getDefaultReturnValue(Class returnType) {
        switch(returnType.getName()) {
            case "boolean":
                return false;
            case "byte":
            case "short":
            case "int":
            case "long":
            case "char":
                return 0;
            case "float":
            case "double":
                return 0.0;
            default:
                return null;
        }
    }
}
