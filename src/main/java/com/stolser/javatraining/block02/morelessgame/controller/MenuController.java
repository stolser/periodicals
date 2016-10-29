package com.stolser.javatraining.block02.morelessgame.controller;

import com.stolser.javatraining.block02.morelessgame.model.menu.Menu;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

public class MenuController {
    private Menu menu;
    private ViewPrinter view;

    public MenuController(Menu menu, ViewPrinter view) {
        this.menu = menu;
        this.view = view;
    }


}
