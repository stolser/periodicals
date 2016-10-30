package com.stolser.javatraining.block02.morelessgame.model;

public class Game {
    private static int randomMinDefault = 0;
    private static int randomMaxDefault = 100;

    public void start() {
        System.out.println("Start a Game...End the Game.");
    }

    public static void setRandomMaxDefault(int newValue) {
        if(newValue < 10 || newValue > 1000) throw new IllegalArgumentException();
        randomMaxDefault = newValue;
    }

}
