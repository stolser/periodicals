package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.menu.*;
import com.stolser.javatraining.block02.morelessgame.model.Environment;

import java.util.Arrays;

public final class MenuGenerator {
    private MenuGenerator() {
    }

    static public MenuItem newMainMenu(Environment environment) {
        MenuItem playGame = new MenuItem(null, "playGame", 1);
        playGame.setCommand(new PlayGameCommand(environment));

        MenuItem setLowerBound = new MenuItem(null, "setLowerBound", 21);
        setLowerBound.setCommand(new SetLowerBoundCommand(environment));

        MenuItem setUpperBound = new MenuItem(null, "setUpperBound", 22);
        setUpperBound.setCommand(new SetUpperBoundCommand(environment));

        MenuItem setLanguage = new MenuItem(null, "setLanguage", 23);
        setLanguage.setCommand(new SetLanguageCommand(environment));

        MenuItem settings = new MenuItem(Arrays.asList(setLowerBound, setUpperBound, setLanguage),
                "settings", 2);

        MenuItem instructions = new MenuItem(null, "instructions", 3);
        instructions.setCommand(new DisplayInstructionsCommand(environment));

        MenuItem about = new MenuItem(null, "about", 4);
        about.setCommand(new DisplayAboutCommand(environment));

        MenuItem exit = new MenuItem(null, "exit", 5);

        return new MenuItem(
                Arrays.asList(playGame, settings, instructions, about, exit),
                "mainMenu", 0);
    }
}
