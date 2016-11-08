package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.Record;
import com.stolser.javatraining.block04.recordbook.model.UserPhone;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.ValidatedInput;
import com.stolser.javatraining.view.ViewPrinter;

import java.util.List;

import static com.stolser.javatraining.block04.recordbook.model.UserPhone.UserPhoneType;
import static com.stolser.javatraining.controller.EnumUtils.*;

class UserPhoneController {
    // "67", "97"
    private static final String REGEX_PHONE_MOBILE_CODE = "\\d{2}";
    // "44", "4592"
    private static final String REGEX_PHONE_CITY_CODE = "\\d{2,4}";
    // "123 45" OR "123 456" OR "123 45 67"
    private static final String REGEX_PHONE_NUMBER = "(\\d{3}\\s{1}\\d{2})" +
            "|(\\d{3}\\s{1}\\d{3})" +
            "|(\\d{3}\\s{1}\\d{2}\\s{1}\\d{2})";
    // "+38(044)555-55-55"
    private static final String REGEX_PHONE = "^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$";
    private static final String BEGINNING_MESSAGE = "---------------------\nEntering phone numbers.";
    private static final String CHOOSE_PHONE_TYPE_TEXT = "Choose a phone type %s: ";
    private static final String PHONE_TYPE_QUESTION = "Is the next phone number mobile? ";
    private static final String PHONE_CODE_TEXT = "Enter a phone code";
    private static final String PHONE_NUMBER_TEXT = "Enter a phone number";
    private static final String MORE_PHONES_QUESTION = "Do you have more phones? ";

    private InputReader input;
    private ValidatedInput validatedInput;
    private ViewPrinter output;
    private String phoneCode;
    private boolean phoneIsMobile;
    private String phoneNumber;
    private UserPhoneType phoneType;

    UserPhoneController(InputReader input, ValidatedInput validatedInput, ViewPrinter output) {
        this.input = input;
        this.validatedInput = validatedInput;
        this.output = output;
    }

    void readUserPhonesAndSaveInto(Record newRecord) {
        boolean thereAreMorePhones;
        List<Integer> phoneTypeValidInput;
        String phoneTypePromptText;

        ValidInputOptions codeInputOptions = getValidInputOptionsFor(UserPhoneType.class);
        phoneTypeValidInput = codeInputOptions.getOptions();
        phoneTypePromptText = String.format(CHOOSE_PHONE_TYPE_TEXT, codeInputOptions.getPromptingMessage());

        output.printlnString(BEGINNING_MESSAGE);

        do {
            getPhoneCodeFromUser();
            getPhoneNumberFromUser();
            getPhoneTypeFromUser(phoneTypeValidInput, phoneTypePromptText);

            newRecord.addPhone(new UserPhone(phoneCode, phoneNumber, phoneIsMobile, phoneType));

            output.printString(MORE_PHONES_QUESTION);
            thereAreMorePhones = input.readYesNoValue();

        } while (thereAreMorePhones);

    }

    private void getPhoneCodeFromUser() {
        output.printString(PHONE_TYPE_QUESTION);
        phoneIsMobile = input.readYesNoValue();
        String phoneCodeRegex = (phoneIsMobile) ? REGEX_PHONE_MOBILE_CODE : REGEX_PHONE_CITY_CODE;
        phoneCode = validatedInput.getValidatedStringInput(PHONE_CODE_TEXT, false, phoneCodeRegex);
    }

    private void getPhoneNumberFromUser() {
        phoneNumber = validatedInput.getValidatedStringInput(PHONE_NUMBER_TEXT, false, REGEX_PHONE_NUMBER);
    }

    private void getPhoneTypeFromUser(List<Integer> phoneTypeValidInput, String phoneTypePromptText) {
        int phoneTypeUserInput = validatedInput.getValidatedIntegerInput(phoneTypePromptText,
                phoneTypeValidInput);

        phoneType = getEnumByOrdinal(UserPhoneType.class, phoneTypeUserInput);

        if (phoneType == null) {
            throw new IllegalStateException("phoneType must NOT be null here.");
        }
    }
}
