package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

/**
 * Is an action executed when menu item 'Instructions' is chosen,
 * namely it displays the game instructions.
 */
public class DisplayInstructionsCommand implements MenuCommand {
    private ViewPrinter output;

    public DisplayInstructionsCommand(Environment environment) {
        this.output = environment.getViewPrinter();
    }

    @Override
    public void execute() {
        output.printlnMessageWithKey("generalMessages", "menu.instructionsText");
    }
}
