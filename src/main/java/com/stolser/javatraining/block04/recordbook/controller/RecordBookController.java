package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.*;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.RegexValidator;
import com.stolser.javatraining.model.Environment;
import com.stolser.javatraining.view.ViewGenerator;
import com.stolser.javatraining.view.ViewPrinter;

import java.time.Instant;
import java.util.*;

import static com.stolser.javatraining.block04.recordbook.model.UserAddress.*;
import static com.stolser.javatraining.block04.recordbook.model.UserPhone.*;

public class RecordBookController {
    // "firstName", "lastName", "extraName", "nickname"
    private static final String REGEX_NAME_PART = "[a-zA-Z]{2, 20}";
    private static final String REGEX_COMMENT = ".{0, 200}";
    // "67", "97"
    private static final String REGEX_PHONE_MOBILE_CODE = "\\d{2}";
    // "44", "4592"
    private static final String REGEX_PHONE_CITY_CODE = "\\d{2, 4}";
    // "123 45" OR "123 456" OR "123 45 67"
    private static final String REGEX_PHONE_NUMBER = "(\\d{3}\\s{1}\\d{2})" +
            "\\|(\\d{3}\\s{1}\\d{3})\\|(\\d{3}\\s{1}\\d{2}\\s{1}\\d{2})";
    // "+38(044)555-55-55"
    private static final String REGEX_PHONE = "^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$";
    // "nick@mail.com"
    private static final String REGEX_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    // "http://www.my-site.com"
    private static final String REGEX_URL = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";
    // "stolser111"
    private static final String REGEX_SKYPE = "[a-zA-Z][a-zA-Z0-9\\.,\\-_]{5,31}";
    // "40 let Oktiabria"
    private static final String REGEX_LOCALITY_STREET_NAME = "[\\w\\s]{2,31}";
    // "15", "122B", "1020"
    private static final String REGEX_HOUSE_APT_NUMBER = "[a-zA-Z\\d]{1,4}";

    private RecordBook recordBook;
    private ViewGenerator viewGenerator;
    private InputReader input;
    private ViewPrinter output;
    private Record newRecord;

    public RecordBookController(RecordBook recordBook, Environment environment) {
        this.recordBook = recordBook;
        this.viewGenerator = environment.getViewGenerator();
        this.input = environment.getInputReader();
        this.output = environment.getViewPrinter();
    }

    public void update() {
        readUserFullName();
        readComment();
        readUserGroups();
        readUserPhones();
        readUserEmail();
        readUserSkype();
        readUserAddress();

        recordBook.addRecord(newRecord);
        newRecord.setCreationDate(Instant.now());
    }

    private void readUserFullName() {
        String firstName;
        String lastName;
        String extraName;
        String nickname;

        firstName = getValidatedStringInput("First/Given/Personal Name", false, REGEX_NAME_PART);
        lastName = getValidatedStringInput("Last/Surname/Family Name", false, REGEX_NAME_PART);
        extraName = getValidatedStringInput("Middle/Patronymic Name", true, REGEX_NAME_PART);
        nickname = getValidatedStringInput("Nickname", true, REGEX_NAME_PART);

        UserName userName = new UserName(firstName, lastName, extraName, nickname);
        newRecord = new Record(userName);
    }

    private void readComment() {
        String comment = getValidatedStringInput("comment", true, REGEX_COMMENT);
        newRecord.setComment(comment);
    }

    private void readUserGroups() {
        Set<UserGroup> chosenGroups = new HashSet<>();
        List<Integer> validInput;
        StringBuilder builder;
        int userInput;
        boolean validInputExist;

        do {
            builder = new StringBuilder("Choose a group ( ");
            validInput = new ArrayList<>();

            for (UserGroup group: UserGroup.values()) {
                if (! chosenGroups.contains(group)) {
                    builder.append(group.toString());
                    builder.append(" - ");
                    builder.append(group.ordinal());
                    builder.append("; ");
                    validInput.add(group.ordinal());
                }
            }

            validInputExist = (validInput.size() > 0);

            if (validInputExist) {
                String promptText = builder.append("): ").toString();
                userInput = getValidatedIntegerInput(promptText, validInput);

                for (UserGroup group: UserGroup.values()) {
                    if (group.ordinal() == userInput) {
                        newRecord.addGroup(group);
                        chosenGroups.add(group);
                        break;
                    }
                }
            }

        /* during the current iteration we have added one group, so
        * if validInput.size() == 1 there will be no group to add during the next iteration. */
        } while (validInput.size() > 1);
    }

    private void readUserPhones() {
        boolean phoneIsMobile;
        String phoneCode;
        String phoneNumber;
        UserPhoneType phoneType = null;
        String phoneCodeRegex;
        boolean thereIsMorePhones;

        List<Integer> validInput = new ArrayList<>();
        StringBuilder builder = new StringBuilder("Choose a phone type ( ");
        for (UserPhoneType type: UserPhoneType.values()) {
            int ordinal = type.ordinal();
            builder.append(type.toString());
            builder.append(" - ");
            builder.append(ordinal);
            builder.append("; ");
            validInput.add(ordinal);
        }
        String phoneCodePromptText = builder.append("): ").toString();

        do {
            output.printString("Is the next phone number mobile? ");
            phoneIsMobile = input.readYesNoValue();
            phoneCodeRegex = (phoneIsMobile) ? REGEX_PHONE_MOBILE_CODE : REGEX_PHONE_CITY_CODE;
            phoneCode = getValidatedStringInput("phone code", false, phoneCodeRegex);
            phoneNumber = getValidatedStringInput("phone number", false, REGEX_PHONE_NUMBER);
            int phoneTypeUserInput = getValidatedIntegerInput(phoneCodePromptText, validInput);

            for (UserPhoneType type: UserPhoneType.values()) {
                if (type.ordinal() == phoneTypeUserInput) {
                    phoneType = type;
                }
            }

            if (phoneType == null) {
                throw new IllegalStateException("phoneType must NOT be null");
            }

            UserPhone newPhone = new UserPhone(phoneCode, phoneNumber, phoneIsMobile, phoneType);
            newRecord.addPhone(newPhone);

            output.printString("Do you have more phones? ");
            thereIsMorePhones = ! input.readYesNoValue();

        } while (thereIsMorePhones);

    }

    private void readUserEmail() {
        String email = getValidatedStringInput("email", false, REGEX_EMAIL);
        newRecord.setEmail(email);

    }

    private void readUserSkype() {
        String skype = getValidatedStringInput("skype", true, REGEX_SKYPE);
        newRecord.setSkype(skype);
    }

    private void readUserAddress() {
        LocalityType localityType;
        String localityName;
        StreetType streetType;
        String streetName;
        String houseNumber;
        String apartmentNumber;


    }

    private String getValidatedStringInput(String promptLabel, boolean canBeEmpty, String regex) {
        String userInput;
        boolean userInputIsValid;
        String canBeEmptyLabel = "";

        if (canBeEmpty) {
            canBeEmptyLabel = " (can be empty)";
        }

        do {
            output.printString(String.format("Enter %s%s: ", promptLabel, canBeEmptyLabel));
            userInput = input.readString();

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

    private int getValidatedIntegerInput(String promptText, List<Integer> validInput) {
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

    public void displayRecords() {
        System.out.println("displaying RecordBook...");

    }
}
