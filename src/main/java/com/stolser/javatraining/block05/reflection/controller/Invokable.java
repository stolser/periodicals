package com.stolser.javatraining.block05.reflection.controller;

import java.lang.annotation.*;

/**
 * Indicates that a method will be invoked a specified number of times if isActive = true.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Invokable {
    /**
     * @return {@code true} if this method will be invoked and {@code false} if all invocations
     * will be suppressed.
     */
    boolean isActive() default true;

    /**
     * @return the actual number of times this method will be invoked for each user's call of this method.
     * If {@code times <= 0} then this method will not be invoked at all.
     * If {@code isActive = false} all calls will be blocked regardless this parameter.<br />
     * If method annotated with {@code @Invokable} returns a value then actually it will return the result of
     * the last invocation.
     */
    int times() default 1;
}
