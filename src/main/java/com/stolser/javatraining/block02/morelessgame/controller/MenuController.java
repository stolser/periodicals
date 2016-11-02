package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

public class MenuController {
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

    public void processUserInput() {
        MenuItem chosenMenuItem;

        do {
            showMenu();

            do {
                output.printMessageWithKey("generalMessages", "menu.makeachoice");
                int userChoice = readUserChoice();
                chosenMenuItem = mainMenu.getItemByOptionId(userChoice);
                if (chosenMenuItem == null) output.printMessageWithKey("generalMessages", "input.menuoption.error");
            } while (chosenMenuItem == null);

            if ("exit".equals(chosenMenuItem.getSystemName())) break;

            chosenMenuItem.chooseMenu();

        } while (true);

    }

    private int readUserChoice() {
        return input.readIntValue();
    }

    private void showMenu() {
        output.printString(viewGenerator.getMainMenuView(mainMenu));
    }
}
