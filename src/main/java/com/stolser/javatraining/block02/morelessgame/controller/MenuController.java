package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.model.menu.Menu;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

public class MenuController {
    private Menu menu;
    private ViewFactory viewFactory;
    private InputReader input;
    private ViewPrinter output;
    private ViewGenerator viewGenerator;

    public MenuController(Menu menu, ViewFactory viewFactory, InputReader input) {
        this.menu = menu;
        this.viewFactory = viewFactory;
        this.input = input;
        this.output = viewFactory.getViewPrinter();
        this.viewGenerator = viewFactory.getViewGenerator(output);
    }

    public void processUserInput() {
        showMenu();
        int userChoice = readUserChoice();

    }

    private int readUserChoice() {
        return input.readIntValue();
    }

    private void showMenu() {
        output.printMessage(viewGenerator.getMenuView(menu));

    }
}
