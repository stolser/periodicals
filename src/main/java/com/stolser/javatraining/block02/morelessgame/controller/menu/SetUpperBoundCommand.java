package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.google.common.collect.Range;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.MoreLessGame;
import com.stolser.javatraining.view.ViewPrinter;

import java.text.MessageFormat;

/**
 * Is an action executed when menu item 'Set Upper Bound' is chosen,
 * namely it asks a user a new value and set it for the game.
 * These changes take effect immediately.
 */
public class SetUpperBoundCommand implements MenuCommand {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String INPUT_BOUND_LIMIT_ERROR = "input.boundLimit.error";
    private static final String MENU_ENTER_NEW_UPPER_BOUND = "menu.enterNewUpperBound";
    private ViewPrinter output;
    private InputReader input;

    public SetUpperBoundCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        MoreLessGame.setUpperBoundDefault(getNewValueFromUser());
    }

    private int getNewValueFromUser() {
        int newValue;
        boolean userEnteredIncorrectValue;

        do {
            askUserToEnterNewValue();
            newValue = input.readIntValue();
            userEnteredIncorrectValue = ! MoreLessGame.isValueForUpperBoundOk(newValue);

            if (userEnteredIncorrectValue) {
                output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_BOUND_LIMIT_ERROR);
            }

        } while (userEnteredIncorrectValue);

        return newValue;
    }

    private void askUserToEnterNewValue() {
        Range<Integer> upperBoundLimits = MoreLessGame.getUpperBoundLimits();
        output.printString(MessageFormat.format(
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_ENTER_NEW_UPPER_BOUND),
                output.getLocalizedNumber(upperBoundLimits.lowerEndpoint()),
                output.getLocalizedNumber(upperBoundLimits.upperEndpoint())));
    }
}
