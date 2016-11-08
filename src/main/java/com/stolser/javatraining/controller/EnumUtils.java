package com.stolser.javatraining.controller;

import com.google.common.base.Preconditions;

import java.util.*;

import static com.google.common.base.Preconditions.*;

public class EnumUtils {

    public static <T extends Enum<T>> ValidInputOptions getValidInputOptionsFor(Class<T> enumType,
                                                                                Set<T> excludeOpts) {
        checkNotNull(enumType);
        checkNotNull(excludeOpts);

        StringBuilder builder = new StringBuilder("{ ");
        List<Integer> validInput = new ArrayList<>();
        Set<T> validOpts = EnumSet.allOf(enumType);
        validOpts.removeAll(excludeOpts);

        validOpts.stream()
            .forEach(enumItem -> {
                int ordinal = enumItem.ordinal();

                builder.append(enumItem.toString());
                builder.append(" - ");
                builder.append(ordinal);
                builder.append("; ");

                validInput.add(ordinal);
        });

        String promptingMessage = builder.append(" }").toString();

        return new ValidInputOptions(promptingMessage, validInput);
    }

    public static <T extends Enum<T>> ValidInputOptions getValidInputOptionsFor(Class<T> enumType) {
        return getValidInputOptionsFor(enumType, Collections.emptySet());
    }

    public static class ValidInputOptions {
        private String promptingMessage;
        private List<Integer> options;

        private ValidInputOptions(String promptingMessage, List<Integer> options) {
            this.promptingMessage = promptingMessage;
            this.options = options;
        }

        public String getPromptingMessage() {
            return promptingMessage;
        }

        public List<Integer> getOptions() {
            return options;
        }
    }
}
