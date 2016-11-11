package com.stolser.javatraining.controller.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {
    private static final String PARAMS_DELIMITER = ", ";
    private static final String MODIFIER_DELIMITER = " ";

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

        return String.join(MODIFIER_DELIMITER, labels);
    }

    public static String getParamsAsString(Class<?>[] parameterTypes) {
        List<String> labels = Arrays.stream(parameterTypes)
                .map(paramType -> getShortNameAsString(paramType.getName()))
                .collect(Collectors.toList());

        return String.join(PARAMS_DELIMITER, labels);
    }

    public static String getShortNameAsString(String fullName) {

        return thisIsPackage(fullName) ? splitNameAndGetLastPart(fullName) : fullName;
    }

    private static boolean thisIsPackage(String fullName) {
        return fullName.contains(".");
    }

    private static String splitNameAndGetLastPart(String fullName) {
        String shortName;
        String[] nameParts = fullName.split("\\.");
        shortName = nameParts[nameParts.length - 1];
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
