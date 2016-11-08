package com.stolser.javatraining.block04.recordbook.model;

import com.stolser.javatraining.block04.recordbook.controller.RecordBookController;

public class RecordBookApp {

    public void start() {
        Environment environment = Environment.newInstance();
        RecordBook recordBook = new RecordBook("Personal RecordBook");
        RecordBookController controller = new RecordBookController(recordBook, environment);

        controller.processUserInput();
        controller.displayRecords();
    }

}
