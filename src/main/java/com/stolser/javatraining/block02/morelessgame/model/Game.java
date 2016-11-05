package com.stolser.javatraining.block02.morelessgame.model;

public interface Game {
    /**
     * Setups the initial state of this game along with all necessary components
     * including input and output streams.
     * This method must be called before method {@code start()} and it can be called only once
     * on the same instance. Otherwise in both cases an {@code IllegalStateException} occurs.
     * @param environment provides access to the components necessary for interacting with a user.
     */
    void setup(Environment environment);

    /**
     * Starts this instance of a game.
     */
    void start();
}
