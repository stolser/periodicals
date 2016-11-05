package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

/**
 * Is an action executed when menu item 'Instructions' is chosen,
 * namely it displays the game instructions.
 */
public class DisplayInstructionsCommand implements MenuCommand {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String MENU_INSTRUCTIONS_TEXT = "menu.instructionsText";

    private ViewPrinter output;

    public DisplayInstructionsCommand(Environment environment) {
        this.output = environment.getViewPrinter();
    }

    @Override
    public void execute() {
        output.printlnMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_INSTRUCTIONS_TEXT);
    }
}
