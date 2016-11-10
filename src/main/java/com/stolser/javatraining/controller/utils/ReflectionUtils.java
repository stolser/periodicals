package com.stolser.javatraining.controller.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
    public static String getModifiesAsString(int modifiers) {
        List<String> labels = new ArrayList<>();

        if (Modifier.isPublic(modifiers)) {
            labels.add("public");
        }

        if (Modifier.isProtected(modifiers)) {
            labels.add("protected");
        }

        if (Modifier.isPrivate(modifiers)) {
            labels.add("private");
        }

        if (Modifier.isAbstract(modifiers)) {
            labels.add("abstract");
        }

        if (Modifier.isStatic(modifiers)) {
            labels.add("static");
        }

        if (Modifier.isFinal(modifiers)) {
            labels.add("final");
        }

        if (Modifier.isNative(modifiers)) {
            labels.add("native");
        }

        if (Modifier.isStrict(modifiers)) {
            labels.add("strictfp");
        }

        if (Modifier.isSynchronized(modifiers)) {
            labels.add("synchronized");
        }

        if (Modifier.isTransient(modifiers)) {
            labels.add("transient");
        }

        if (Modifier.isVolatile(modifiers)) {
            labels.add("volatile");
        }

        return String.join(" ", labels);
    }

    public static String getParamsAsString(Class<?>[] parameterTypes) {
        List<String> labels = new ArrayList<>();
        Arrays.stream(parameterTypes)
                .forEach(paramType -> labels.add(getShortNameAsString(paramType.getName())));

        return String.join(", ", labels);
    }

    public static String getShortNameAsString(String fullName) {
        String shortName;

        if (fullName.contains(".")) {
            String[] nameParts = fullName.split("\\.");
            shortName = nameParts[nameParts.length - 1];
        } else {
            shortName = fullName;
        }

        return shortName;
    }

    public static String getAnnotationsAsMultiLineString(Annotation[] annotations) {
        StringBuilder builder = new StringBuilder();

        if (annotations.length > 0) {
            Arrays.stream(annotations)
                    .forEach(annotation -> builder.append(String.format("\t@%s\n",
                            getShortNameAsString(annotation.annotationType().getName()))));
        }

        return builder.toString();
    }
}
