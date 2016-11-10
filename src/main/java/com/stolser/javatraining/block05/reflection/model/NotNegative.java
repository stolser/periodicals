package com.stolser.javatraining.block05.reflection.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target(value = {FIELD, PARAMETER, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNegative {
    byte defaultValue() default 0;
}
