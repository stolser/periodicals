package com.stolser.javatraining.webproject.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestCommand {
    String process(HttpServletRequest request, HttpServletResponse response);
}
