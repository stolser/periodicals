package com.stolser.javatraining.block05.reflection.model.vehicle;

public class TooHeavyCargo extends RuntimeException {
    private static final String WEIGHT_TOO_HEAVY_TEXT = "Sorry, the weight is too heavy!";

    public TooHeavyCargo() {
        this(WEIGHT_TOO_HEAVY_TEXT);
    }

    public TooHeavyCargo(String message) {
        super(message);
    }
}
