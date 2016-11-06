package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.google.common.collect.Range;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.MoreLessGame;
import com.stolser.javatraining.view.ViewPrinter;

import java.text.MessageFormat;

public class SetLowerBoundCommand implements MenuCommand {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String INPUT_BOUND_LIMIT_ERROR = "input.boundLimit.error";
    private static final String MENU_ENTER_NEW_LOWER_BOUND = "menu.enterNewLowerBound";

    private ViewPrinter output;
    private InputReader input;

    public SetLowerBoundCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        MoreLessGame.setLowerBoundDefault(getNewValueFromUser());
    }

    private int getNewValueFromUser() {
        int newValue;
        boolean userEnteredIncorrectValue;

        do {
            askUserToEnterNewValue();
            newValue = input.readIntValue();
            userEnteredIncorrectValue = ! MoreLessGame.isValueForLowerBoundOk(newValue);

            if (userEnteredIncorrectValue) {
                output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_BOUND_LIMIT_ERROR);
            }

        } while (userEnteredIncorrectValue);

        return newValue;
    }

    private void askUserToEnterNewValue() {
        Range<Integer> lowerBoundLimits = MoreLessGame.getLowerBoundLimits();
        output.printString(MessageFormat.format(
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_ENTER_NEW_LOWER_BOUND),
                output.getLocalizedNumber(lowerBoundLimits.lowerEndpoint()),
                output.getLocalizedNumber(lowerBoundLimits.upperEndpoint())));
    }
}
