package com.stolser.javatraining.block02.morelessgame.model.game;

import com.stolser.javatraining.block02.morelessgame.controller.MoreLessGameController;
import com.stolser.javatraining.block02.morelessgame.model.Environment;

public class GameFactory {
    private Environment environment;

    private GameFactory(Environment environment) {
        this.environment = environment;
    }

    public Game newMoreLessGame() {
        Game newGame = new MoreLessGameController(new MoreLessGame());
        newGame.setup(environment);

        return newGame;
    }

    public static GameFactory newInstance(Environment environment) {
        return new GameFactory(environment);
    }
}
