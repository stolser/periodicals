package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.ConsoleInputReader;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.*;
import com.stolser.javatraining.block02.morelessgame.view.ConsoleViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.Arrays;

public class Application {

    public void start() {
        ViewFactory viewFactory = ConsoleViewFactory.getInstance();
        ViewPrinter viewPrinter = viewFactory.getViewPrinter();
        ViewGenerator viewGenerator = viewFactory.getViewGenerator(viewPrinter);
        InputReader inputReader = new ConsoleInputReader(viewFactory.getViewPrinter());
        Environment environment = new Environment(inputReader, viewPrinter, viewGenerator);
        MenuItem mainMenu = new MenuGenerator(environment).newMainMenu();

        MenuController controller = new MenuController(environment, mainMenu);

        controller.processUserInput();
    }
}
