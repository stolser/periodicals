package com.stolser.javatraining.block02.morelessgame.model;

public class GameFactory {
    private Environment environment;

    private GameFactory(Environment environment) {
        this.environment = environment;
    }

    public Game newMoreLessGame() {
        Game newGame = new MoreLessGame();
        newGame.setup(environment);

        return newGame;
    }

    public static GameFactory newInstance(Environment environment) {
        return new GameFactory(environment);
    }
}
