package com.stolser.javatraining.block02.morelessgame.model.menu;

import java.util.List;

public class MenuItemFactory {
    private MenuItemFactory() {}

    public static MenuItemFactory newInstance() {
        return new MenuItemFactory();
    }

    public MenuItem newMainMenuItem(List<MenuItem> items, String systemName, int optionId) {
        checkArguments(systemName, optionId);

        return new MainMenuItem(items, systemName, optionId);
    }

    private void checkArguments(String systemName, int optionId) {
        if ((systemName == null) || (optionId < 0)) {
            throw new IllegalArgumentException();
        }
    }
}
