package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }
}
