package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuGenerator;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.model.Environment;
import com.stolser.javatraining.model.Environments;

/**
 * The main class of the More-Less game.
 */
public class Application {
    /**
     * Instantiate all classes necessary for the application and
     * starts a new game.
     */
    public void start() {
        Environment environment = Environments.newConsoleEnvironment();
        MenuItem mainMenu = MenuGenerator.newMainMenu(environment);
        MenuController controller = new MenuController(environment, mainMenu);

        controller.processUserInput();
    }
}
