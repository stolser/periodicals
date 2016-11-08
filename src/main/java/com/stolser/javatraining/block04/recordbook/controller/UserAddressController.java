package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.Record;
import com.stolser.javatraining.block04.recordbook.model.UserAddress;
import com.stolser.javatraining.controller.ValidatedInput;

import java.util.List;

import static com.stolser.javatraining.block04.recordbook.model.UserAddress.LocalityType;
import static com.stolser.javatraining.block04.recordbook.model.UserAddress.StreetType;
import static com.stolser.javatraining.controller.EnumUtils.*;

/**
 * A controller for asking a user info about address, processing and saving it into a current record.
 *
 */
class UserAddressController {
    // "03022"
    private static final String REGEX_POST_CODE = "\\d{5}";
    // "40 let Oktiabria"
    private static final String REGEX_LOCALITY_STREET_NAME = "[\\w\\s]{2,31}";
    // "15", "122B", "1020"
    private static final String REGEX_HOUSE_APT_NUMBER = "[a-zA-Z\\d]{1,4}";
    private static final String POST_CODE_TEXT = "Enter a post code (XXXXX)";
    private static final String LOCALITY_TYPE_TEXT = "Choose a locality type %s: ";
    private static final String LOCALITY_NAME_TEXT = "Enter a locality name (up to 31 letters and digits)";
    private static final String STREET_TYPE_TEXT = "Choose a street type %s: ";
    private static final String STREET_NAME_TEXT = "Enter a street name (up to 31 letters and digits)";
    private static final String HOUSE_NUMBER_TEXT = "Enter a house number (from 1 to 4 letters and digits)";
    private static final String APARTMENT_NUMBER_TEXT = "Enter an apartment number " +
                                                        "(from 1 to 4 letters and digits)";

    private ValidatedInput validatedInput;
    private String postCode;
    private LocalityType localityType;
    private String localityName;
    private StreetType streetType;
    private String streetName;
    private String houseNumber;
    private String apartmentNumber;

    UserAddressController(ValidatedInput validatedInput) {
        this.validatedInput = validatedInput;
    }

    /**
     * @param newRecord a current record which will be populated with address data entered by a user.
     */
    void readAddressAndSaveInto(Record newRecord) {
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
        postCode = validatedInput.getValidatedStringInput(POST_CODE_TEXT, true, REGEX_POST_CODE);
    }

    private void readLocalityType() {
        ValidInputOptions inputOptions = getValidInputOptionsFor(LocalityType.class);
        List<Integer> validInput = inputOptions.getOptions();
        String promptText = String.format(LOCALITY_TYPE_TEXT, inputOptions.getPromptingMessage());

        int userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);

        localityType = getEnumByOrdinal(LocalityType.class, userInput);
    }

    private void readLocalityName() {
        localityName = validatedInput
                .getValidatedStringInput(LOCALITY_NAME_TEXT, false, REGEX_LOCALITY_STREET_NAME);
    }

    private void readStreetType() {
        ValidInputOptions inputOptions = getValidInputOptionsFor(StreetType.class);
        List<Integer> validInput = inputOptions.getOptions();
        String promptText = String.format(STREET_TYPE_TEXT, inputOptions.getPromptingMessage());

        int userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);

        streetType = getEnumByOrdinal(StreetType.class, userInput);
    }

    private void readStreetName() {
        streetName = validatedInput
                .getValidatedStringInput(STREET_NAME_TEXT, false, REGEX_LOCALITY_STREET_NAME);
    }

    private void readHouseNumber() {
        houseNumber = validatedInput
                .getValidatedStringInput(HOUSE_NUMBER_TEXT, false, REGEX_HOUSE_APT_NUMBER);
    }

    private void readApartmentNumber() {
        apartmentNumber = validatedInput
                .getValidatedStringInput(APARTMENT_NUMBER_TEXT, false, REGEX_HOUSE_APT_NUMBER);
    }

}
