package com.stolser.javatraining.block02.morelessgame.model.menu;

import java.util.List;

public class MenuItem {
    private List<MenuItem> items;
    private String systemName;
    private int optionId;
    private MenuCommand command;

    public MenuItem(List<MenuItem> items, String systemName, int optionId) {
        this.items = items;
        this.systemName = systemName;
        this.optionId = optionId;
    }

    public boolean isSubMenu() {
        return (getItems() != null);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public String getSystemName() {
        return systemName;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setCommand(MenuCommand command) {
        this.command = command;
    }

    public void chooseMenu() {
        command.execute();
    }


    /**
     * Tries to find in this menu a MenuItem that is not a subMenu with this {@code optionId}
     * among all items at any level. An item is not a subMenu and doesn't contains items of its one.
     *
     * @return a found MenuItem or {@code null} otherwise.
     */
    public MenuItem getItemByOptionId(int optionId) {

        for (MenuItem item : items) {
            if (item.isSubMenu()) {
                MenuItem result = item.getItemByOptionId(optionId);
                if (result != null) return result;
            } else {
                if (item.getOptionId() == optionId) return item;
            }
        }

        return null;
    }
}
