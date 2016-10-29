package com.stolser.javatraining.block02.morelessgame.model.menu;

import java.util.List;

public class MenuItem implements Menu {
    private List<Menu> items;
    private String systemName;
    private int optionId;

    public MenuItem(List<Menu> items, String systemName, int optionId) {
        this.items = items;
        this.systemName = systemName;
        this.optionId = optionId;
    }

    @Override
    public List<Menu> getItems() {
        return items;
    }

    @Override
    public String getSystemName() {
        return systemName;
    }

    @Override
    public int getOptionId() {
        return optionId;
    }
}
