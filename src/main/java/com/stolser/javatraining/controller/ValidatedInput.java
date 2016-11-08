package com.stolser.javatraining.controller;

import com.stolser.javatraining.view.ViewPrinter;

import java.util.List;

public class ValidatedInput {
    private InputReader input;
    private ViewPrinter output;

    public ValidatedInput(InputReader input, ViewPrinter output) {
        this.input = input;
        this.output = output;
    }

    public String getValidatedStringInput(String promptLabel, boolean canBeEmpty, String regex) {
        String userInput;
        boolean userInputIsValid;
        String canBeEmptyLabel = "";

        if (canBeEmpty) {
            canBeEmptyLabel = " (can be empty)";
        }

        do {
            output.printString(String.format("%s%s: ", promptLabel, canBeEmptyLabel));
            userInput = input.readLine();

            if ("".equals(userInput) && canBeEmpty) {
                return userInput;
            }

            userInputIsValid = RegexValidator.validateString(regex, userInput);

            if (! userInputIsValid) {
                output.printlnString("Error! Repeat you attempt.");
            }

        } while (! userInputIsValid);

        return userInput;
    }

    public int getValidatedIntegerInput(String promptText, List<Integer> validInput) {
        int userInput;
        boolean userInputIsValid;

        do {
            output.printString(promptText);
            userInput = input.readIntValue();
            userInputIsValid = validInput.contains(userInput);

            if (! userInputIsValid) {
                output.printlnString("Error! Repeat you attempt.");
            }

        } while (! userInputIsValid);

        return userInput;
    }
}
