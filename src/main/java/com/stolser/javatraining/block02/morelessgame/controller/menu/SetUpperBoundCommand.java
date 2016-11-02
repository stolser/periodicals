package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.google.common.collect.Range;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.Game;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * Is an action executed when menu item 'Set Random Max Limit' is chosen,
 * namely it asks a user a new value and set it for all
 * {@link com.stolser.javatraining.block02.morelessgame.model.Game}s.
 * These changes take effect immediately.
 */
public class SetUpperBoundCommand implements MenuCommand {
    private ViewPrinter output;
    private InputReader input;

    public SetUpperBoundCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        Game.setUpperBoundDefault(getNewValueFromUser());
    }

    private int getNewValueFromUser() {
        int newValue;
        boolean userEnteredIncorrectValue;

        do {
            askUserToEnterNewValue();
            newValue = input.readIntValue();
            userEnteredIncorrectValue = ! Game.isValueForUpperBoundOk(newValue);

            if (userEnteredIncorrectValue) {
                output.printMessageWithKey("generalMessages", "input.randomMax.error");
            }

        } while (userEnteredIncorrectValue);

        return newValue;
    }

    private void askUserToEnterNewValue() {
        Range<Integer> upperBoundLimits = Game.getUpperBoundLimits();
        output.printString(MessageFormat.format(
                output.getMessageWithKey("generalMessages", "menu.enterNewUpperBound"),
                output.getLocalizedNumber(upperBoundLimits.lowerEndpoint()),
                output.getLocalizedNumber(upperBoundLimits.upperEndpoint())));
    }
}
