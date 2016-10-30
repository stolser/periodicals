package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;

import java.util.Arrays;

public class MenuGenerator {
    private Environment environment;

    public MenuGenerator(Environment environment) {
        this.environment = environment;
    }

    public MenuItem newMainMenu() {
        MenuItem playGame = new MenuItem(null, "playGame", 1);
        playGame.setCommand(new PlayGameCommand(environment));

        MenuItem setRandomMax = new MenuItem(null, "setRandomMax", 21);
        setRandomMax.setCommand(new SetRandomMaxCommand(environment));

        MenuItem setLanguage = new MenuItem(null, "setLanguage", 22);
        setLanguage.setCommand(new SetLanguageCommand(environment));

        MenuItem settings = new MenuItem(Arrays.asList(setRandomMax, setLanguage), "settings", 2);

        MenuItem instructions = new MenuItem(null, "instructions", 3);
        instructions.setCommand(new DisplayInstructionsCommand(environment));

        MenuItem about = new MenuItem(null, "about", 4);
        about.setCommand(new DisplayAboutCommand(environment));

        MenuItem exit = new MenuItem(null, "exit", 5);

        MenuItem mainMenu = new MenuItem(
                Arrays.asList(playGame, settings, instructions, about, exit),
                "mainMenu", 1);

        return mainMenu;
    }
}
