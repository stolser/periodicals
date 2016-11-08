package com.stolser.javatraining.block02.morelessgame.controller.menu;

import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.block02.morelessgame.model.Environment;
import com.stolser.javatraining.model.SystemLocale;
import com.stolser.javatraining.view.ViewPrinter;

import java.text.MessageFormat;
import java.util.*;

/**
 * Is an action executed when menu item 'Set Language' is chosen,
 * namely it asks a user to choose a new locale from the set of available ones.
 * These changes take effect immediately.
 */
public class SetLanguageCommand implements MenuCommand {
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";
    private static final String INPUT_NEW_LOCALE_ERROR = "input.newLocale.error";
    private static final String MENU_ENTER_NEW_LOCALE = "menu.enterNewLocale";

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
        Locale newLocale;

        do {
            askUserToChooseNewLocale();

            newLocale = selectLocaleByUserInput(input.readIntValue());

            if (newLocale == null) {
                informUserAboutError();
            }

        } while (newLocale == null);

        return newLocale;
    }

    private void informUserAboutError() {
        output.printMessageWithKey(GENERAL_MESSAGE_BUNDLE, INPUT_NEW_LOCALE_ERROR);
    }

    private void askUserToChooseNewLocale() {
        StringBuilder localeOptionsBuilder = new StringBuilder();
        for (SystemLocale systemLocale: SystemLocale.values()) {
            localeOptionsBuilder
                .append(output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, systemLocale.getSystemName()))
                .append(" - ")
                .append(systemLocale.getMenuOption())
                .append("; ");
        }

        String message = MessageFormat.format(
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_ENTER_NEW_LOCALE),
                localeOptionsBuilder.toString());

        output.printString(message);
    }

    private Locale selectLocaleByUserInput(int userInput) {
        return SystemLocale.getLocaleByMenuOption(userInput);
    }
}
