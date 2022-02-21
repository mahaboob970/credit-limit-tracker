package com.ecg.creditlimittracker.controller;

import com.ecg.creditlimittracker.service.CreditLimitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class CreditLimitTrackerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private CreditLimitTrackerService service;

    @GetMapping("/process-data")
    @ResponseBody
    public String processData()  {

        this.service.showConflicts();
        return new String("Tracking");
    }


}
