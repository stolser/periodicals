package com.stolser.javatraining.block04.recordbook.controller;

import com.stolser.javatraining.block04.recordbook.controller.RecordBookController;
import com.stolser.javatraining.block04.recordbook.model.Environment;
import com.stolser.javatraining.block04.recordbook.model.recordbook.RecordBook;

public class RecordBookApp {

    public void start() {
        Environment environment = Environment.newInstance();
        RecordBook recordBook = new RecordBook("Personal RecordBook");
        RecordBookController controller = new RecordBookController(recordBook, environment);

        controller.processUserInput();
        controller.displayRecords();
    }

}
