package com.stolser.javatraining.block02.morelessgame.model.menu;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MenuItemTest {

    @Test
    public void getMenuItemByOptionIdShouldReturnCorrectItem() throws Exception {
        MenuItem item1 = new MenuItem(null, "item1", 1);
        MenuItem item2 = new MenuItem(null, "item2", 2);
        MenuItem item3 = new MenuItem(Arrays.asList(item1, item2), "item3", 3);
        MenuItem item4 = new MenuItem(null, "item4", 4);
        MenuItem item5 = new MenuItem(null, "item5", 5);
        MenuItem menu = new MenuItem(Arrays.asList(item3, item4, item5), "menu", 6);

        assertEquals(item1, menu.getItemByOptionId(1));
        assertEquals(item2, menu.getItemByOptionId(2));
        assertEquals(null, menu.getItemByOptionId(3));
        assertEquals(null, menu.getItemByOptionId(6));
        assertEquals(null, menu.getItemByOptionId(7));

    }
}