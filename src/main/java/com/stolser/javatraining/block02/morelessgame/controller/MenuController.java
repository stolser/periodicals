package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

/**
 * The main class that is responsible for processing user input
 * and dispatching him to an appropriate action class.
 */
public class MenuController {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String CHOOSE_MENU_ITEM_TEXT = "menu.makeachoice";
    private static final String INPUT_MENU_OPTION_ERROR = "input.menuoption.error";
    private static final String EXIT_SYSTEM_NAME = "exit";

    private MenuItem mainMenu;
    private InputReader input;
    private ViewPrinter output;
    private ViewGenerator viewGenerator;

    public MenuController(Environment environment, MenuItem mainMenu) {
        this.input = environment.getInputReader();
        this.output = environment.getViewPrinter();
        this.viewGenerator = environment.getViewGenerator();
        this.mainMenu = mainMenu;
    }

    /**
     * Shows the main menu and prompts a user to make a choice inside an infinite loop util they
     * chose 'exit' item. Then delegates to a corresponding
     * {@link com.stolser.javatraining.block02.morelessgame.controller.menu.MenuCommand} instance.
     */
    public void processUserInput() {
        MenuItem chosenMenuItem;
        boolean displayMenuAgain = true;

        do {
            showMenu();
            chosenMenuItem = askUserAndGetChosenMenuItem();

            if (userWantsToExit(chosenMenuItem)) {
                displayMenuAgain = false;
            } else {
                chosenMenuItem.chooseMenu();
            }

        } while (displayMenuAgain);

    }

    private MenuItem askUserAndGetChosenMenuItem() {
        MenuItem chosenMenuItem;

        do {
            output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, CHOOSE_MENU_ITEM_TEXT);
            int userChoice = readUserChoice();
            chosenMenuItem = mainMenu.getItemByOptionId(userChoice);
            if (chosenMenuItem == null) output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_MENU_OPTION_ERROR);

        } while (chosenMenuItem == null);

        return chosenMenuItem;
    }

    private boolean userWantsToExit(MenuItem chosenMenuItem) {
        return EXIT_SYSTEM_NAME.equals(chosenMenuItem.getSystemName());
    }

    private int readUserChoice() {
        return input.readIntValue();
    }

    private void showMenu() {
        output.printString(viewGenerator.getMainMenuView(mainMenu));
    }
}
