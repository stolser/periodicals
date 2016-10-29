package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Game;

public class PlayGameCommand implements MenuCommand {
    @Override
    public void execute() {
        new Game().start();
    }
}
