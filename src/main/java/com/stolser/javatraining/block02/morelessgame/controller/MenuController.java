package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.ResourceBundle;

public class MenuController {
    private MenuItem menu;
    private ViewFactory viewFactory;
    private InputReader input;
    private ViewPrinter output;
    private ViewGenerator viewGenerator;

    public MenuController(MenuItem menu, ViewFactory viewFactory, InputReader input) {
        this.menu = menu;
        this.viewFactory = viewFactory;
        this.input = input;
        this.output = viewFactory.getViewPrinter();
        this.viewGenerator = viewFactory.getViewGenerator(output);
    }

    public void processUserInput() {
        MenuItem chosenMenuItem;

        do {
            showMenu();

            do {
                output.printMessageWithKey("generalMessages", "menu.makeachoice");
                int userChoice = readUserChoice();
                chosenMenuItem = menu.getMenuItemByOptionId(userChoice);
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
        output.printlnString(viewGenerator.getMenuView(menu));

    }
}
