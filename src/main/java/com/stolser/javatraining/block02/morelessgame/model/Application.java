package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.ConsoleInputReader;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuGenerator;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.view.ConsoleViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

public class Application {
    public void start() {
        Environment environment = createEnvironment();
        MenuItem mainMenu = MenuGenerator.newMainMenu(environment);

        MenuController controller = new MenuController(environment, mainMenu);

        controller.processUserInput();
    }

    private Environment createEnvironment() {
        ViewFactory viewFactory = ConsoleViewFactory.getInstance();
        ViewPrinter viewPrinter = viewFactory.getViewPrinter();
        ViewGenerator viewGenerator = viewFactory.getViewGenerator();
        InputReader inputReader = new ConsoleInputReader(viewPrinter);

        return new Environment(inputReader, viewPrinter, viewGenerator);
    }
}
