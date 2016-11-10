package com.stolser.javatraining.block04.recordbook.view;

import com.stolser.javatraining.block04.recordbook.model.recordbook.Record;
import com.stolser.javatraining.block04.recordbook.model.recordbook.RecordBook;
import com.stolser.javatraining.block04.recordbook.model.user.UserPhone;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ViewGeneratorImpl implements ViewGenerator {
    private static final String DATE_FORMAT = "dd:MM:YYYY HH:mm:ss";
    private static final String RECORD_TITLE_TEXT = "----------- record %d -----------\n";
    private static final String USER_NAME_TEXT = "User name: %s\n";
    private static final String COMMENT_TEXT = "Comment: %s\n";
    private static final String GROUPS_TEXT = "Groups: %s\n";
    private static final String PHONES_TEXT = "Phones: %s\n";
    private static final String EMAIL_TEXT = "Email: %s\n";
    private static final String SKYPE_TEXT = "Skype: %s\n";
    private static final String ADDRESS_TEXT = "Address: %s\n";
    private static final String CREATION_DATE_TEXT = "Creation date: %s\n";
    private static final String LAST_MODIFIED_DATE_TEXT = "Last modified date: %s\n\n";

    private StringBuilder builder = new StringBuilder();
    private RecordBook recordBook;
    private Set<Record> records;

    @Override
    public String getRecordBookView(RecordBook recordBook) {
        this.recordBook = recordBook;
        records = recordBook.getRecords();

        appendHeader();
        appendAllRecords();
        appendFooter();

        return builder.toString();
    }

    private void appendHeader() {
        builder.append(String.format("================================== Record Book (%s) " +
                "==================================\n", recordBook.getName()));
    }

    private void appendAllRecords() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        int recordIndex = 1;

        for (Record record : records) {
            List<String> phoneStrings = record.getPhones()
                    .stream()
                    .map(UserPhone::getFullNumber)
                    .collect(Collectors.toList());

            builder.append(String.format(RECORD_TITLE_TEXT, recordIndex));
            builder.append(String.format(USER_NAME_TEXT, record.getUserName().getFullForm()));
            builder.append(String.format(COMMENT_TEXT, record.getComment()));
            builder.append(String.format(GROUPS_TEXT, record.getGroups()));
            builder.append(String.format(PHONES_TEXT, phoneStrings));
            builder.append(String.format(EMAIL_TEXT, record.getEmail()));
            builder.append(String.format(SKYPE_TEXT, record.getSkype()));
            builder.append(String.format(ADDRESS_TEXT, record.getAddress().getFullAddress()));
            builder.append(String.format(CREATION_DATE_TEXT,
                    dateFormat.format(record.getCreationDate().toEpochMilli())));
            builder.append(String.format(LAST_MODIFIED_DATE_TEXT,
                    dateFormat.format(record.getLastModifDate().toEpochMilli())));

            recordIndex++;
        }
    }

    private void appendFooter() {
        builder.append("============================================ Footer " +
                "=============================================\n");
    }
}
