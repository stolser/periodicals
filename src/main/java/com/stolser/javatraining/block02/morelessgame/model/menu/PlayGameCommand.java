package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.Game;

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
