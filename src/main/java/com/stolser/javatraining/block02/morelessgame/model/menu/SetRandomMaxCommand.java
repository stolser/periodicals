package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Game;
import com.stolser.javatraining.block02.morelessgame.view.ViewFactory;
import com.stolser.javatraining.block02.morelessgame.view.ViewGenerator;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

public class SetRandomMaxCommand implements MenuCommand {
    private ViewPrinter output;
    private ViewGenerator viewGenerator;
    private InputReader input;

    public SetRandomMaxCommand(ViewFactory viewFactory, InputReader input) {
        this.output = viewFactory.getViewPrinter();
        this.viewGenerator = viewFactory.getViewGenerator(output);
        this.input = input;
    }

    @Override
    public void execute() {
        int randomMax = getNewRandomMaxFromUser();
        Game.setRandomMaxDefault(randomMax);
    }

    private int getNewRandomMaxFromUser() {
        int newValue;

        do {
            output.printMessageWithKey("generalMessages", "menu.enterNewRandomMaxValue");
            newValue = input.readIntValue();
            if(userEnteredIncorrectValue(newValue)) {
                output.printMessageWithKey("generalMessages", "input.randomMax.error");
            }

        } while (userEnteredIncorrectValue(newValue));

        return newValue;
    }

    private boolean userEnteredIncorrectValue(int value) {
        return value < Game.RANDOM_MAX_LOW_LIMIT || value > Game.RANDOM_MAX_HIGH_LIMIT;
    }
}
