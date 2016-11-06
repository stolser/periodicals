package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.menu.*;
import com.stolser.javatraining.model.Environment;

import java.util.Arrays;

/**
 * A utility class encapsulating static methods for creating different menus for the application.
 */
public final class MenuGenerator {
    private static final String PLAY_GAME_LABEL = "playGame";
    private static final int PLAY_GAME_OPTION_ID = 1;
    private static final String SET_LOWER_BOUND_LABEL = "setLowerBound";
    private static final int SET_LOWER_BOUND_OPTION_ID = 21;
    private static final String SET_UPPER_BOUND_LABEL = "setUpperBound";
    private static final int SET_UPPER_BOUND_OPTION_ID = 22;
    private static final String SET_LANGUAGE_LABEL = "setLanguage";
    private static final int SET_LANGUAGE_OPTION_ID = 23;
    private static final String SETTINGS_LABEL = "settings";
    private static final int SETTINGS_OPTION_ID = 2;
    private static final String INSTRUCTIONS_LABEL = "instructions";
    private static final int INSTRUCTIONS_OPTION_ID = 3;
    private static final String ABOUT_LABEL = "about";
    private static final int ABOUT_OPTION_ID = 4;
    private static final String EXIT_LABLE = "exit";
    private static final int EXIT_OPTION_ID = 5;
    private static final String MAIN_MENU_LABEL = "mainMenu";
    private static final int MAIN_MENU_OPTION_ID = 0;

    private MenuGenerator() {}

    /**
     * Encapsulates the structure of the Main Menu (sub-menus and menu-items)
     * and behavior of each menu-item.
     * @param environment - contains all helper classes used by this app.
     * @return an instance of the Main Menu for the app.
     */
    static public MenuItem newMainMenu(Environment environment) {
        MenuItemFactory factory = MenuItemFactory.newInstance();

        MenuItem playGame = factory.newMainMenuItem(null, PLAY_GAME_LABEL, PLAY_GAME_OPTION_ID);
        playGame.setCommand(new PlayGameCommand(environment));

        MenuItem setLowerBound = factory.newMainMenuItem(null, SET_LOWER_BOUND_LABEL, SET_LOWER_BOUND_OPTION_ID);
        setLowerBound.setCommand(new SetLowerBoundCommand(environment));

        MenuItem setUpperBound = factory.newMainMenuItem(null, SET_UPPER_BOUND_LABEL, SET_UPPER_BOUND_OPTION_ID);
        setUpperBound.setCommand(new SetUpperBoundCommand(environment));

        MenuItem setLanguage = factory.newMainMenuItem(null, SET_LANGUAGE_LABEL, SET_LANGUAGE_OPTION_ID);
        setLanguage.setCommand(new SetLanguageCommand(environment));

        MenuItem settings = factory.newMainMenuItem(Arrays.asList(setLowerBound, setUpperBound, setLanguage),
                SETTINGS_LABEL, SETTINGS_OPTION_ID);

        MenuItem instructions = factory.newMainMenuItem(null, INSTRUCTIONS_LABEL, INSTRUCTIONS_OPTION_ID);
        instructions.setCommand(new DisplayInstructionsCommand(environment));

        MenuItem about = factory.newMainMenuItem(null, ABOUT_LABEL, ABOUT_OPTION_ID);
        about.setCommand(new DisplayAboutCommand(environment));

        MenuItem exit = factory.newMainMenuItem(null, EXIT_LABLE, EXIT_OPTION_ID);

        return factory.newMainMenuItem(
                Arrays.asList(playGame, settings, instructions, about, exit),
                MAIN_MENU_LABEL, MAIN_MENU_OPTION_ID);
    }
}
