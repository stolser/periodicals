package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.block02.morelessgame.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.block02.morelessgame.view.ViewPrinter;

import java.util.*;

/**
 * Is an action executed when menu item 'Set Language' is chosen,
 * namely it asks a user to choose a new locale from the set of available ones.
 * These changes take effect immediately.
 */
public class SetLanguageCommand implements MenuCommand {
    private static final Set<Integer> CORRECT_LOCALE_OPTIONS;
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String INPUT_NEW_LOCALE_ERROR = "input.newLocale.error";
    private static final String MENU_ENTER_NEW_LOCALE = "menu.enterNewLocale";

    static {
        CORRECT_LOCALE_OPTIONS = new HashSet<>();
        CORRECT_LOCALE_OPTIONS.add(1);
        CORRECT_LOCALE_OPTIONS.add(2);
    }

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
        output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_NEW_LOCALE_ERROR);
    }

    private void askUserToChooseNewLocale() {
        output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_ENTER_NEW_LOCALE);
    }

    private boolean userEnteredIncorrectLocaleValue(int userInput) {
        return ! CORRECT_LOCALE_OPTIONS.contains(userInput);
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
                throw new IllegalArgumentException("Illegal user input for a locale");
        }
        return newLocale;
    }
}
