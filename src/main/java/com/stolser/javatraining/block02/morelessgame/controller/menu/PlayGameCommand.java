package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.GameFactory;
import com.stolser.javatraining.block02.morelessgame.model.MoreLessGame;

/**
 * Is an action executed when menu item 'Play MoreLessGame' is chosen,
 * namely it creates and starts a new MoreLessGame.
 */
public class PlayGameCommand implements MenuCommand {
    private Environment environment;

    public PlayGameCommand(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void execute() {
        GameFactory.newInstance(environment).newMoreLessGame().start();
    }
}
