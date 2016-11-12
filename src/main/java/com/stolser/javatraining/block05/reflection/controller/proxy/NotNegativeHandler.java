package com.stolser.javatraining.block05.reflection.controller.proxy;

import com.stolser.javatraining.block05.reflection.controller.NotNegative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NotNegativeHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotNegativeHandler.class);

    private Object proxied;
    private List<Field> NotNegativeNumberFields;

    public NotNegativeHandler(Object proxied) {
        this.proxied = proxied;
        this.NotNegativeNumberFields = Arrays
                .stream(this.proxied.getClass().getDeclaredFields())
                .filter(field -> !Modifier.isFinal(field.getModifiers()))
                .filter(field -> field.isAnnotationPresent(NotNegative.class))
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(proxied);

                        if (fieldValueIsNumberPrimitive(fieldValue)) {
                            if (fieldValueIsNegative(fieldValue)) {
                                throw new IllegalStateException(String.format("The initial state is forbidden. " +
                                        "The field '%s' is negative.", field.getName()));
                            }

                            return true;
                        }

                        return false;

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(proxied, args);

        for (Field field : NotNegativeNumberFields) {
            LOGGER.debug(String.format("checking the '%s' field for negativity...", field.getName()));

            field.setAccessible(true);
            Object fieldValue = field.get(proxied);

            if (fieldValueIsNegative(fieldValue)) {
                throw new IllegalStateException(String.format("The field '%s' is negative " +
                        "after invoking the '%s()' method.", field.getName(), method.getName()));
            }

            LOGGER.debug(String.format("the value = %s. OK", fieldValue));
        }

        return result;
    }

    private boolean fieldValueIsNumberPrimitive(Object fieldValue) {
        return (fieldValue instanceof Byte) || (fieldValue instanceof Short)
                || (fieldValue instanceof Integer) || (fieldValue instanceof Long)
                || (fieldValue instanceof Float) || (fieldValue instanceof Double);
    }

    private boolean fieldValueIsNegative(Object fieldValue) {
        if ((fieldValue instanceof Byte) && ((Byte) fieldValue < 0)) return true;
        if ((fieldValue instanceof Short) && ((Short) fieldValue < 0)) return true;
        if ((fieldValue instanceof Integer) && ((Integer) fieldValue < 0)) return true;
        if ((fieldValue instanceof Long) && ((Long) fieldValue < 0)) return true;
        if ((fieldValue instanceof Float) && ((Float) fieldValue < 0.0)) return true;
        if ((fieldValue instanceof Double) && ((Double) fieldValue < 0.0)) return true;

        return false;
    }
}
