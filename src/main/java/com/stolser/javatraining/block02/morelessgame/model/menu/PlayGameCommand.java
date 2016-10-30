package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.Game;

/**
 * Is an action executed when menu item 'Play Game' is chosen,
 * namely it creates and starts a new Game.
 */
public class PlayGameCommand implements MenuCommand {
    private Environment environment;

    public PlayGameCommand(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void execute() {
        new Game(environment).start();
    }
}
