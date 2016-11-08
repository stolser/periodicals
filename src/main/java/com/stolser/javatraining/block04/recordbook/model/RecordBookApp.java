package com.stolser.javatraining.block04.recordbook.model;

import com.stolser.javatraining.block04.recordbook.controller.RecordBookController;
import com.stolser.javatraining.model.Environment;
import com.stolser.javatraining.model.Environments;

public class RecordBookApp {

    public void start() {
        Environment environment = Environments.newMoreLessConsoleEnvironment();
        RecordBook recordBook = new RecordBook("Personal RecordBook");
        RecordBookController controller = new RecordBookController(recordBook, environment);

        controller.update();
        controller.displayRecords();
    }

}
