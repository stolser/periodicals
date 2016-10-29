package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.ConsoleInputReader;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.model.menu.PlayGameCommand;
import com.stolser.javatraining.block02.morelessgame.view.ConsoleViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;

import java.util.Arrays;

public class Application {

    public void start() {
        ViewFactory viewFactory = ConsoleViewFactory.getInstance();
        InputReader inputReader = new ConsoleInputReader(viewFactory.getViewPrinter());
        MenuItem mainMenu = generateMainMenu();
        MenuController controller = new MenuController(mainMenu, viewFactory, inputReader);

        controller.processUserInput();
    }

    private MenuItem generateMainMenu() {
        MenuItem playGame = new MenuItem(null, "playGame", 1);
        playGame.setCommand(new PlayGameCommand());

        MenuItem setRandomMax = new MenuItem(null, "setRandomMax", 21);
        MenuItem setLanguage = new MenuItem(null, "setLanguage", 22);
        MenuItem settings = new MenuItem(Arrays.asList(setRandomMax, setLanguage), "settings", 2);

        MenuItem instructions = new MenuItem(null, "instructions", 3);
        MenuItem about = new MenuItem(null, "about", 4);
        MenuItem exit = new MenuItem(null, "exit", 5);

        MenuItem mainMenu = new MenuItem(
                Arrays.asList(playGame, settings, instructions, about, exit),
                "mainMenu", 1);

        return mainMenu;
    }
}
