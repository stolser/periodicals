package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.menu.MenuCommand;

import java.util.List;

public interface MenuItem {
    /**
     * @return true is this menu-item is a sub-menu and contains other menu-items at the next level down.
     */
    boolean isSubMenu();

    /**
     * @return a list of all menu-items residing at the next level down.
     */
    List<MenuItem> getItems();

    /**
     * Is used to get localized label for this menu-item.
     * @return localized label for this menu-item.
     */
    String getSystemName();

    /**
     * @return integer value that will be associated with this menu-item in the application.
     */
    int getOptionId();

    /**
     * @param command represents an action that will be executed when this
     * menu-item is chosen by a user.
     */
    void setCommand(MenuCommand command);

    /**
     * activates the command of this menu-item.
     */
    void chooseMenu();

    /**
     * Tries to find in this menu a MenuItem that is not a subMenu with this {@code optionId}
     * among all items at any level. An item is not a subMenu and doesn't contains items of its one.
     *
     * @return a found MenuItem or {@code null} otherwise.
     */
    MenuItem getItemByOptionId(int optionId);
}
