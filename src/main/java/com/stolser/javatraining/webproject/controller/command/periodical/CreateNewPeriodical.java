package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateNewPeriodical implements RequestProcessor {
    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        return "periodicals/createPeriodical";
    }
}
