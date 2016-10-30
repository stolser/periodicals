package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.Game;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

/**
 * Is an action executed when menu item 'Set Random Max Limit' is chosen,
 * namely it asks a user a new value and set it for all
 * {@link com.stolser.javatraining.block02.morelessgame.model.Game}s.
 * These changes take effect immediately.
 */
public class SetRandomMaxCommand implements MenuCommand {
    private ViewPrinter output;
    private InputReader input;

    public SetRandomMaxCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        Game.setRandomMaxDefault(getNewRandomMaxFromUser());
    }

    private int getNewRandomMaxFromUser() {
        int newValue;

        do {
            askUserToEnterNewValue();
            newValue = input.readIntValue();
            if (userEnteredIncorrectValue(newValue)) {
                output.printMessageWithKey("generalMessages", "input.randomMax.error");
            }

        } while (userEnteredIncorrectValue(newValue));

        return newValue;
    }

    private void askUserToEnterNewValue() {
        output.printMessageWithKey("generalMessages", "menu.enterNewRandomMaxValue");
    }

    private boolean userEnteredIncorrectValue(int value) {
        return (value < Game.RANDOM_MAX_LOW_LIMIT
                || value > Game.RANDOM_MAX_HIGH_LIMIT
                || value <= Game.getRandomMinDefault());
    }
}
