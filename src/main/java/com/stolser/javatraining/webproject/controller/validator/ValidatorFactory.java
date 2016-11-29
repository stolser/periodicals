package com.stolser.javatraining.webproject.controller.validator;

public class ValidatorFactory {
    private ValidatorFactory() {}

    private static class InstanceHolder {
        private static final ValidatorFactory INSTANCE = new ValidatorFactory();
    }

    public static ValidatorFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Validator newValidator(String paramName) {

        switch (paramName) {
            case "signInUsername":
                return new SignInUsernameValidator();
            default:
                throw new IllegalArgumentException("There is no validator for such a parameter!");
        }
    }
}
