package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.*;
import com.stolser.javatraining.block04.recordbook.view.ViewGenerator;
import com.stolser.javatraining.controller.EnumUtils;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.ValidatedInput;
import com.stolser.javatraining.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

import static com.stolser.javatraining.block04.recordbook.model.UserPhone.*;
import static com.stolser.javatraining.controller.EnumUtils.*;

public class RecordBookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordBookController.class);

    // "firstName", "lastName", "extraName", "nickname"
    private static final String REGEX_NAME_PART = "[a-zA-Z]{2,20}";
    private static final String REGEX_COMMENT = ".{0,200}";
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
    // "nick@mail.com"
    private static final String REGEX_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    // "http://www.my-site.com"
    private static final String REGEX_URL = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";
    // "stolser111"
    private static final String REGEX_SKYPE = "[a-zA-Z][a-zA-Z0-9\\.,\\-_]{5,31}";

    private RecordBook recordBook;
    private ViewGenerator viewGenerator;
    private InputReader input;
    private ValidatedInput validatedInput;
    private ViewPrinter output;
    private Record newRecord;

    public RecordBookController(RecordBook recordBook, Environment environment) {
        this.recordBook = recordBook;
        this.viewGenerator = environment.getViewGenerator();
        this.input = environment.getInputReader();
        this.output = environment.getViewPrinter();
    }

    public void update() {
        boolean thereIsMoreData;
        validatedInput = new ValidatedInput(input, output);

        output.printlnString("Please, enter new records.");

        do {
            readUserFullName();
            readComment();
            readUserGroups();
            readUserPhones();
            readUserEmail();
            readUserSkype();
            new UserAddressController(input, validatedInput, output).readAddressAndSaveInto(newRecord);

            recordBook.addRecord(newRecord);
            newRecord.setCreationDate(Instant.now());

            output.printString("Would you like to continue entering data?");
            thereIsMoreData = input.readYesNoValue();

        } while (thereIsMoreData);
    }

    private void readUserFullName() {
        String firstName;
        String lastName;
        String extraName;
        String nickname;

        firstName = validatedInput
                .getValidatedStringInput("Enter First/Given/Personal Name", false, REGEX_NAME_PART);
        lastName = validatedInput
                .getValidatedStringInput("Enter Last/Surname/Family Name", false, REGEX_NAME_PART);
        extraName = validatedInput
                .getValidatedStringInput("Enter Middle/Patronymic Name", true, REGEX_NAME_PART);
        nickname = validatedInput
                .getValidatedStringInput("Enter Nickname", true, REGEX_NAME_PART);

        UserName userName = new UserName(firstName, lastName, extraName, nickname);
        newRecord = new Record(userName);
    }

    private void readComment() {
        String comment = validatedInput.getValidatedStringInput("Enter a comment", true, REGEX_COMMENT);
        newRecord.setComment(comment);
    }

    private void readUserGroups() {
        Set<UserGroup> chosenGroups = new HashSet<>();
        List<Integer> validInput;
        int userInput;

        output.printlnString("---------------------\nEntering user groups (must be at list one).");

        do {
            ValidInputOptions inputOptions = getValidInputOptionsFor(UserGroup.class, chosenGroups);
            validInput = inputOptions.getOptions();

            String promptText = String.format("Choose a group %s: ", inputOptions.getPromptingMessage());
            userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);
            LOGGER.debug("userInput = {}", userInput);

            UserGroup group = EnumUtils.getEnumByOrdinal(UserGroup.class, userInput);
            LOGGER.debug("group = {}", group);

            newRecord.addGroup(group);
            chosenGroups.add(group);

            if (validInput.size() - 1 >= 0) {
                output.printString("Would you like to add one group more?");
                boolean noMoreGroups = ! input.readYesNoValue();

                if (noMoreGroups) {
                    break;
                }
            }

        /* during the current iteration we have added one group, so
        * if validInput.size() == 1 there will be no group to add during the next iteration. */
        } while (validInput.size() - 1 >= 0);
    }

    private void readUserPhones() {
        boolean phoneIsMobile;
        String phoneCode;
        String phoneNumber;
        UserPhoneType phoneType = null;
        String phoneCodeRegex;
        boolean thereAreMorePhones;
        List<Integer> phoneTypeValidInput;
        String phoneTypePromptText;

        ValidInputOptions codeInputOptions = getValidInputOptionsFor(UserPhoneType.class);
        phoneTypeValidInput = codeInputOptions.getOptions();
        phoneTypePromptText = String.format("Choose a phone type %s: ", codeInputOptions.getPromptingMessage());

        output.printlnString("---------------------\nEntering phone numbers.");

        do {
            output.printString("Is the next phone number mobile? ");
            phoneIsMobile = input.readYesNoValue();
            phoneCodeRegex = (phoneIsMobile) ? REGEX_PHONE_MOBILE_CODE : REGEX_PHONE_CITY_CODE;
            phoneCode = validatedInput.getValidatedStringInput("Enter a phone code", false, phoneCodeRegex);

            phoneNumber = validatedInput.getValidatedStringInput("Enter a phone number", false, REGEX_PHONE_NUMBER);
            int phoneTypeUserInput = validatedInput.getValidatedIntegerInput(phoneTypePromptText,
                    phoneTypeValidInput);

            phoneType = EnumUtils.getEnumByOrdinal(UserPhoneType.class, phoneTypeUserInput);

            if (phoneType == null) {
                throw new IllegalStateException("phoneType must NOT be null here.");
            }

            UserPhone newPhone = new UserPhone(phoneCode, phoneNumber, phoneIsMobile, phoneType);
            newRecord.addPhone(newPhone);

            output.printString("Do you have more phones? ");
            thereAreMorePhones = input.readYesNoValue();

        } while (thereAreMorePhones);

    }

    private void readUserEmail() {
        String email = validatedInput.getValidatedStringInput("Enter a email", false, REGEX_EMAIL);
        newRecord.setEmail(email);

    }

    private void readUserSkype() {
        String skype = validatedInput.getValidatedStringInput("Enter a skype", true, REGEX_SKYPE);
        newRecord.setSkype(skype);
    }

    public void displayRecords() {
        output.printlnString(viewGenerator.getRecordBookView(recordBook));
    }
}
