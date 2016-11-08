package com.stolser.javatraining.block04.recordbook.view;

import com.stolser.javatraining.block04.recordbook.model.Record;
import com.stolser.javatraining.block04.recordbook.model.RecordBook;
import com.stolser.javatraining.block04.recordbook.model.UserPhone;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ViewGeneratorImpl implements ViewGenerator {
    private static final String DATE_FORMAT = "dd:MM:YYYY HH:mm:ss";

    @Override
    public String getRecordBookView(RecordBook recordBook) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Set<Record> records = recordBook.getRecords();
        StringBuilder builder = new StringBuilder();
        int recordIndex = 1;

        builder.append(String.format("================================== Record Book (%s) " +
                "==================================\n", recordBook.getName()));

        for (Record record: records){
            List<String> phoneStrings = record.getPhones()
                    .stream()
                    .map(UserPhone::getFullNumber)
                    .collect(Collectors.toList());

            builder.append(String.format("----------- record %d -----------\n", recordIndex));
            builder.append(String.format("User name: %s\n", record.getUserName().getFullForm()));
            builder.append(String.format("Comment: %s\n", record.getComment()));
            builder.append(String.format("Groups: %s\n", record.getGroups()));
            builder.append(String.format("Phones: %s\n", phoneStrings));
            builder.append(String.format("Email: %s\n", record.getEmail()));
            builder.append(String.format("Skype: %s\n", record.getSkype()));
            builder.append(String.format("Address: %s\n", record.getAddress().getFullAddress()));
            builder.append(String.format("Creation date: %s\n",
                    dateFormat.format(record.getCreationDate().toEpochMilli())));
            builder.append(String.format("Last modified date: %s\n\n",
                    dateFormat.format(record.getLastModifDate().toEpochMilli())));

            recordIndex++;
        }

        builder.append("======================================= Footer " +
                "========================================\n");

        return builder.toString();
    }
}
