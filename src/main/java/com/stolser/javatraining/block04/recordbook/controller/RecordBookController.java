package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.Environment;
import com.stolser.javatraining.block04.recordbook.model.recordbook.Record;
import com.stolser.javatraining.block04.recordbook.model.recordbook.RecordBook;
import com.stolser.javatraining.block04.recordbook.model.user.UserName;
import com.stolser.javatraining.block04.recordbook.view.ViewGenerator;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.validate.ValidatedInput;
import com.stolser.javatraining.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * The main controller of the RecordBook app. Has methods for adding new records filled with
 * a user input.
 */
public class RecordBookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordBookController.class);

    // "firstName", "lastName", "extraName", "nickname"
    private static final String REGEX_NAME_PART = "[a-zA-Z]{2,20}";
    private static final String REGEX_COMMENT = ".{0,200}";
    // "nick@mail.com"
    private static final String REGEX_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    // "http://www.my-site.com"
    private static final String REGEX_URL = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";
    // "stolser111"
    private static final String REGEX_SKYPE = "[a-zA-Z][a-zA-Z0-9\\.,\\-_]{5,21}";
    private static final String PLEASE_ENTER_NEW_RECORDS = "Please, enter new records.";
    private static final String FIRST_NAME_TEXT = "Enter First/Given/Personal Name (only letters without spaces)";
    private static final String lAST_NAME_TEXT = "Enter Last/Surname/Family Name (only letters without spaces)";
    private static final String EXTRA_NAME_TEXT = "Enter Middle/Patronymic Name (only letters without spaces)";
    private static final String NICKNAME_TEXT = "Enter Nickname (only letters without spaces)";
    private static final String COMMENT_TEXT = "Enter a comment (up to 200 of any symbols)";
    private static final String EMAIL_TEXT = "Enter a email (example@gmail.com)";
    private static final String SKYPE_TEXT = "Enter a skype (yourskype707)";
    private static final String CONTINUE_ENTERING_DATA_QUESTION = "Would you like to continue entering data?";

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

    /**
     * Asks a user about different data, creates a new record, populates it with entered by a user data
     * and adds a new record to this RecordBook. Can be used to add several records in a row.
     */
    public void processUserInput() {
        boolean thereIsMoreData;
        validatedInput = new ValidatedInput(input, output);

        output.printlnString(PLEASE_ENTER_NEW_RECORDS);

        do {
            readUserFullName();
            readComment();
            new UserGroupController(input, validatedInput, output).readUserGroupsAndSaveInto(newRecord);
            new UserPhoneController(input, validatedInput, output).readUserPhonesAndSaveInto(newRecord);
            readUserEmail();
            readUserSkype();
            new UserAddressController(validatedInput).readAddressAndSaveInto(newRecord);

            recordBook.addRecord(newRecord);
            newRecord.setCreationDate(Instant.now());

            output.printString(CONTINUE_ENTERING_DATA_QUESTION);
            thereIsMoreData = input.readYesNoValue();

        } while (thereIsMoreData);
    }

    private void readUserFullName() {
        String firstName;
        String lastName;
        String extraName;
        String nickname;

        firstName = validatedInput
                .getValidatedStringInput(FIRST_NAME_TEXT, false, REGEX_NAME_PART);
        lastName = validatedInput
                .getValidatedStringInput(lAST_NAME_TEXT, false, REGEX_NAME_PART);
        extraName = validatedInput
                .getValidatedStringInput(EXTRA_NAME_TEXT, true, REGEX_NAME_PART);
        nickname = validatedInput
                .getValidatedStringInput(NICKNAME_TEXT, true, REGEX_NAME_PART);

        newRecord = new Record(new UserName(firstName, lastName, extraName, nickname));
    }

    private void readComment() {
        String comment = validatedInput.getValidatedStringInput(COMMENT_TEXT, true, REGEX_COMMENT);
        newRecord.setComment(comment);
    }

    private void readUserEmail() {
        String email = validatedInput.getValidatedStringInput(EMAIL_TEXT, false, REGEX_EMAIL);
        newRecord.setEmail(email);

    }

    private void readUserSkype() {
        String skype = validatedInput.getValidatedStringInput(SKYPE_TEXT, true, REGEX_SKYPE);
        newRecord.setSkype(skype);
    }

    /**
     * Displays all records from this RecordBook.
     */
    public void displayRecords() {
        output.printlnString(viewGenerator.getRecordBookView(recordBook));
    }
}
