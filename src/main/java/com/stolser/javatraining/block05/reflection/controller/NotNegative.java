package com.stolser.javatraining.block05.reflection.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Indicates that annotated variable cannot be negative with optional default value.<br />
 * If {@code defaultValue} is differ from 0 then this value will be assigned to the variable.
 */
@Target(value = {FIELD, PARAMETER, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNegative {
    byte defaultValue() default 0;
}
