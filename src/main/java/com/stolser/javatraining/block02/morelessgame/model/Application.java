package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.ConsoleInputReader;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuGenerator;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.view.ConsoleViewFactory;
import com.stolser.javatraining.view.ViewFactory;
import com.stolser.javatraining.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

/**
 * The main class of the More-Less game.
 */
public class Application {
    /**
     * Instantiate all classes necessary for the application and
     * starts a new game.
     */
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
