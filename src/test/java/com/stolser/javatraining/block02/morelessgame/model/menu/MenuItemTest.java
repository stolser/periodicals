package com.stolser.javatraining.block02.morelessgame.model.menu;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MenuItemTest {

    @Test
    public void getMenuItemByOptionId_Should_ReturnCorrectItem() throws Exception {
        MenuItemFactory factory = MenuItemFactory.newInstance();

        MenuItem item1 = factory.newMainMenuItem(null, "item1", 1);
        MenuItem item2 = factory.newMainMenuItem(null, "item2", 2);
        MenuItem item3 = factory.newMainMenuItem(Arrays.asList(item1, item2), "item3", 3);
        MenuItem item4 = factory.newMainMenuItem(null, "item4", 4);
        MenuItem item5 = factory.newMainMenuItem(null, "item5", 5);
        MenuItem menu = factory.newMainMenuItem(Arrays.asList(item3, item4, item5), "menu", 6);

        assertEquals(item1, menu.getItemByOptionId(1));
        assertEquals(item2, menu.getItemByOptionId(2));
        assertEquals(null, menu.getItemByOptionId(3));
        assertEquals(null, menu.getItemByOptionId(6));
        assertEquals(null, menu.getItemByOptionId(7));

    }
}