package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.Record;
import com.stolser.javatraining.block04.recordbook.model.UserAddress;
import com.stolser.javatraining.block04.recordbook.model.UserPhone;
import com.stolser.javatraining.controller.EnumUtils;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.ValidatedInput;
import com.stolser.javatraining.view.ViewPrinter;

import java.util.List;

import static com.stolser.javatraining.block04.recordbook.model.UserAddress.*;
import static com.stolser.javatraining.controller.EnumUtils.*;
import static com.stolser.javatraining.controller.EnumUtils.getValidInputOptionsFor;

class UserAddressController {
    // "03022"
    private static final String REGEX_POST_CODE = "\\d{5}";
    // "40 let Oktiabria"
    private static final String REGEX_LOCALITY_STREET_NAME = "[\\w\\s]{2,31}";
    // "15", "122B", "1020"
    private static final String REGEX_HOUSE_APT_NUMBER = "[a-zA-Z\\d]{1,4}";

    private InputReader input;
    private ValidatedInput validatedInput;
    private ViewPrinter output;
    private String postCode;
    private LocalityType localityType;
    private String localityName;
    private StreetType streetType;
    private String streetName;
    private String houseNumber;
    private String apartmentNumber;

    public UserAddressController(InputReader input, ValidatedInput validatedInput, ViewPrinter output) {
        this.input = input;
        this.validatedInput = validatedInput;
        this.output = output;
    }

    public void readAddressAndSaveInto(Record newRecord) {
        readPostCode();
        readLocalityType();
        readLocalityName();
        readStreetType();
        readStreetName();
        readHouseNumber();
        readApartmentNumber();

        UserAddress address = new UserAddress(postCode, localityType, localityName, streetType,
                streetName, houseNumber, apartmentNumber);

        newRecord.updateAddress(address);
    }

    private void readPostCode() {
        postCode = validatedInput.getValidatedStringInput("Enter a post code", true, REGEX_POST_CODE);
    }

    private void readLocalityType() {
        ValidInputOptions inputOptions = getValidInputOptionsFor(LocalityType.class);
        List<Integer> validInput = inputOptions.getOptions();
        String promptText = String.format("Choose a locality type %s: ", inputOptions.getPromptingMessage());

        int userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);

        localityType = getEnumByOrdinal(LocalityType.class, userInput);
    }

    private void readLocalityName() {
        localityName = validatedInput
                .getValidatedStringInput("Enter a locality name", false, REGEX_LOCALITY_STREET_NAME);
    }

    private void readStreetType() {
        ValidInputOptions inputOptions = getValidInputOptionsFor(StreetType.class);
        List<Integer> validInput = inputOptions.getOptions();
        String promptText = String.format("Choose a street type %s: ", inputOptions.getPromptingMessage());

        int userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);

        streetType = getEnumByOrdinal(StreetType.class, userInput);
    }

    private void readStreetName() {
        streetName = validatedInput
                .getValidatedStringInput("Enter a street name", false, REGEX_LOCALITY_STREET_NAME);
    }

    private void readHouseNumber() {
        houseNumber = validatedInput
                .getValidatedStringInput("Enter a house number", false, REGEX_HOUSE_APT_NUMBER);
    }

    private void readApartmentNumber() {
        apartmentNumber = validatedInput
                .getValidatedStringInput("Enter an apartment number", false, REGEX_HOUSE_APT_NUMBER);
    }

}
