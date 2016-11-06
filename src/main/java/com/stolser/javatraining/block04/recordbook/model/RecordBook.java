package com.stolser.javatraining.block04.recordbook.model;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecordBook {
    private List<Record> records;
    private String name;
    private String description;

    public RecordBook(String name) {
        this(new ArrayList<>(), name, "");
    }

    public RecordBook(List<Record> records, String name, String description) {
        checkArguments(records, name, description);

        this.records = records;
        this.name = name;
        this.description = description;
    }

    public void addRecord(Record newRecord) {
        checkNotNull(newRecord);
        records.add(newRecord);
    }

    private void checkArguments(List<Record> records, String name, String description) {
        checkNotNull(records);
        checkNotNull(name);
        checkNotNull(description);
    }

    public List<Record> getRecords() {
        return records;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
