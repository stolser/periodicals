package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.google.common.collect.Range;
import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.model.Game;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.text.MessageFormat;

public class SetLowerBoundCommand implements MenuCommand {
    private ViewPrinter output;
    private InputReader input;

    public SetLowerBoundCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        Game.setLowerBoundDefault(getNewValueFromUser());
    }

    private int getNewValueFromUser() {
        int newValue;
        boolean userEnteredIncorrectValue;

        do {
            askUserToEnterNewValue();
            newValue = input.readIntValue();
            userEnteredIncorrectValue = ! Game.isValueForLowerBoundOk(newValue);

            if (userEnteredIncorrectValue) {
                output.printMessageWithKey("generalMessages", "input.boundLimit.error");
            }

        } while (userEnteredIncorrectValue);

        return newValue;
    }

    private void askUserToEnterNewValue() {
        Range<Integer> lowerBoundLimits = Game.getLowerBoundLimits();
        output.printString(MessageFormat.format(
                output.getMessageWithKey("generalMessages", "menu.enterNewLowerBound"),
                output.getLocalizedNumber(lowerBoundLimits.lowerEndpoint()),
                output.getLocalizedNumber(lowerBoundLimits.upperEndpoint())));
    }
}
