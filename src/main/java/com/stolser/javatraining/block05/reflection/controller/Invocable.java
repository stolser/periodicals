package com.stolser.javatraining.block05.reflection.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shows that a method can be invoked through reflection specific number of times <br />
 * if isActive = true.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invocable {
    boolean isActive() default true;
    int times() default 1;
}
