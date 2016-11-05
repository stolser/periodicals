package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

/**
 * Is an action executed when menu item 'About' is chosen,
 * namely it displays info about this program.
 */
public class DisplayAboutCommand implements MenuCommand {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String MENU_ABOUT_TEXT = "menu.aboutText";

    private ViewPrinter output;

    public DisplayAboutCommand(Environment environment) {
        this.output = environment.getViewPrinter();
    }

    @Override
    public void execute() {
        output.printlnMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_ABOUT_TEXT);
    }
}
