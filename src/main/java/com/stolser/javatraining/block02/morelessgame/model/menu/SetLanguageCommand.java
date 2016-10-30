package com.stolser.javatraining.block02.morelessgame.model.menu;

import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.Locale;

public class SetLanguageCommand implements MenuCommand {
    private ViewPrinter output;
    private InputReader input;

    public SetLanguageCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        Locale newLocale = getNewLocaleFromUser();
        output.setLocale(newLocale);
    }

    private Locale getNewLocaleFromUser() {
        int userInput;
        Locale newLocale;

        do {
            output.printMessageWithKey("generalMessages", "menu.enterNewLocale");

            userInput = input.readIntValue();
            if(userEnteredIncorrectLocaleValue(userInput)) {
                output.printMessageWithKey("generalMessages", "input.newLocale.error");
            }

        } while (userEnteredIncorrectLocaleValue(userInput));

        switch (userInput) {
            case 1:
                newLocale = Locale.US;
                break;
            case 2:
                newLocale = new Locale("ru", "RU");
                break;
            default:
                throw new IllegalArgumentException();
        }

        return newLocale;
    }

    private boolean userEnteredIncorrectLocaleValue(int userInput) {
        return (userInput != 1 && userInput != 2);
    }
}
