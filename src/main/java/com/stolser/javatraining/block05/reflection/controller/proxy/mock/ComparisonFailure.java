package com.stolser.javatraining.block05.reflection.controller.proxy.mock;

public class ComparisonFailure extends RuntimeException {
    private Object expected;
    private Object actual;

    public ComparisonFailure(Object expected, Object actual) {
        this(String.format("Actual(%s) != expected(%s)", actual, expected), expected, actual);
    }

    public ComparisonFailure(String message, Object expected, Object actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    public Object getExpected() {
        return expected;
    }

    public Object getActual() {
        return actual;
    }
}
