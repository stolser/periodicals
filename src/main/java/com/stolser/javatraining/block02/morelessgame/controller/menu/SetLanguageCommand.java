package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Is an action executed when menu item 'Set Language' is chosen,
 * namely it asks a user to choose a new locale from the set of available ones.
 * These changes take effect immediately.
 */
public class SetLanguageCommand implements MenuCommand {
    private static final List<Integer> CORRECT_USER_OPTIONS = new ArrayList<>(Arrays.asList(1, 2));
    private ViewPrinter output;
    private InputReader input;

    public SetLanguageCommand(Environment environment) {
        this.output = environment.getViewPrinter();
        this.input = environment.getInputReader();
    }

    @Override
    public void execute() {
        output.setLocale(getNewLocaleFromUser());
    }

    private Locale getNewLocaleFromUser() {
        int userInput;

        do {
            askUserToChooseNewLocale();

            userInput = input.readIntValue();
            if (userEnteredIncorrectLocaleValue(userInput)) {
                informUserAboutError();
            }

        } while (userEnteredIncorrectLocaleValue(userInput));

        return selectLocaleByUserInput(userInput);
    }

    private void informUserAboutError() {
        output.printMessageWithKey("generalMessages", "input.newLocale.error");
    }

    private void askUserToChooseNewLocale() {
        output.printMessageWithKey("generalMessages", "menu.enterNewLocale");
    }

    private boolean userEnteredIncorrectLocaleValue(int userInput) {
        return ! CORRECT_USER_OPTIONS.contains(userInput);
    }

    private Locale selectLocaleByUserInput(int userInput) {
        Locale newLocale;
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
}
