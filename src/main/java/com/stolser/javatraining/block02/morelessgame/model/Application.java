package com.stolser.javatraining.block02.morelessgame.model;

import com.stolser.javatraining.block02.morelessgame.controller.MenuController;
import com.stolser.javatraining.block02.morelessgame.model.menu.Menu;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.view.ConsoleViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Application {
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private Locale locale;

    public Application() {
        this.locale = DEFAULT_LOCALE;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        ViewFactory viewFactory = ConsoleViewFactory.getInstance();
        ViewPrinter viewPrinter = viewFactory.getViewPrinter();
        ViewGenerator viewGenerator = viewFactory.getViewGenerator();

        Menu mainMenu = generateMainMenu();

        MenuController menuController = new MenuController(mainMenu, viewPrinter);




    }

    private Menu generateMainMenu() {
        Menu playGame = new MenuItem(null, "playGame", 1);

        Menu setRandomMax = new MenuItem(null, "setRandomMax", 21);
        Menu setLanguage = new MenuItem(null, "setLanguage", 22);
        Menu settings = new MenuItem(Arrays.asList(setRandomMax, setLanguage), "setLanguage", 2);

        Menu instructions = new MenuItem(null, "instructions", 3);
        Menu about = new MenuItem(null, "about", 4);
        Menu exit = new MenuItem(null, "exit", 5);

        Menu mainMenu = new MenuItem(
                Arrays.asList(playGame, settings, instructions, about, exit),
                "mainMenu", 1);

        return mainMenu;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
