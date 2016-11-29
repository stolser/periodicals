package com.stolser.javatraining.webproject.controller.command.user;

import com.stolser.javatraining.webproject.controller.command.RequestProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayOneUser implements RequestProcessor {
    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {

        return "users/oneUserInfo";
    }
}
