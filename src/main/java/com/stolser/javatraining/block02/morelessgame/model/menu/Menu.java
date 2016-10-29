package com.stolser.javatraining.block02.morelessgame.model.menu;

import java.util.List;

public interface Menu {
    default boolean isSubMenu() {
        return (getItems() != null);
    }

    List<Menu> getItems();
    String getSystemName();
    int getOptionId();

}
