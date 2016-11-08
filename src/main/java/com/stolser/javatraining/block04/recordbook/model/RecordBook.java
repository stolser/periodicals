package com.stolser.javatraining.block04.recordbook.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents an abstraction of a record book that can contain records.
 */
public class RecordBook {
    private Set<Record> records;
    private String name;
    private String description;

    public RecordBook(String name) {
        this(new HashSet<>(), name, "");
    }

    public RecordBook(Set<Record> records, String name, String description) {
        checkArguments(records, name, description);

        this.records = records;
        this.name = name;
        this.description = description;
    }

    /**
     * Tries to add a new record. Since RecordBook contains only unique records <br />
     * duplicates will be discarded.
     * @param newRecord must be unique in order to be added into this RecordBook
     * @return {@code true} if a passed Record was added to records of this RecordBook
     */
    public boolean addRecord(Record newRecord) {
        checkNotNull(newRecord);
        return records.add(newRecord);
    }

    private void checkArguments(Set<Record> records, String name, String description) {
        checkNotNull(records);
        checkNotNull(name);
        checkNotNull(description);
    }

    /**
     * @return a deep copy of records of this RecordBook
     */
    public Set<Record> getRecords() {
        Set<Record> copy = new HashSet<>(records.size());
        copy.addAll(records.stream()
                .map(Record::clone)
                .collect(Collectors.toList()));

        return copy;
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
