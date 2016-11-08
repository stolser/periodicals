package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.model.Record;
import com.stolser.javatraining.block04.recordbook.model.UserGroup;
import com.stolser.javatraining.controller.InputReader;
import com.stolser.javatraining.controller.ValidatedInput;
import com.stolser.javatraining.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.stolser.javatraining.controller.EnumUtils.*;

/**
 * A controller for asking a user info about user groups, processing and saving it into a current record.
 */
class UserGroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupController.class);
    private static final String BEGINNING_MESSAGE = "---------------------\n" +
                                                "Entering user groups (must be at list one).";
    private static final String ADD_MORE_GROUPS_TEXT = "Would you like to add one group more?";

    private InputReader input;
    private ValidatedInput validatedInput;
    private ViewPrinter output;
    private List<Integer> validInput;
    private String promptText;
    private Set<UserGroup> chosenGroups;

    UserGroupController(InputReader input, ValidatedInput validatedInput, ViewPrinter output) {
        this.input = input;
        this.validatedInput = validatedInput;
        this.output = output;
    }

    /**
     * Each user must be attached to at least one use group. But it can be attached to several different groups.
     * @param newRecord a current record which will be populated with user group data entered by a user.
     */
    void readUserGroupsAndSaveInto(Record newRecord) {
        chosenGroups = new HashSet<>();

        output.printlnString(BEGINNING_MESSAGE);

        do {
            setupValidInputAndPromptText();
            UserGroup group = getUserGroupFromUser();

            newRecord.addGroup(group);
            chosenGroups.add(group);

            if (thereAreUnselectedGroups()) {
                output.printString(ADD_MORE_GROUPS_TEXT);
                boolean noMoreGroups = ! input.readYesNoValue();

                if (noMoreGroups) {
                    break;
                }
            }

        } while (thereAreUnselectedGroups());
    }

    private void setupValidInputAndPromptText() {
        ValidInputOptions inputOptions = getValidInputOptionsFor(UserGroup.class, chosenGroups);
        validInput = inputOptions.getOptions();
        promptText = String.format("Choose a group %s: ", inputOptions.getPromptingMessage());
    }

    private UserGroup getUserGroupFromUser() {
        int userInput = validatedInput.getValidatedIntegerInput(promptText, validInput);
        UserGroup group = getEnumByOrdinal(UserGroup.class, userInput);
        LOGGER.debug("chosen group = {}", group);

        return group;
    }

    private boolean thereAreUnselectedGroups() {
        /* during the current iteration we have added one group, so
        * if validInput.size() == 1 there will be no group to add during the next iteration. */
        return validInput.size() > 1;
    }
}
