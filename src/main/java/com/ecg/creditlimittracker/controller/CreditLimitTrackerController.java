package com.ecg.creditlimittracker.controller;

import com.ecg.creditlimittracker.service.CreditLimitTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CreditLimitTrackerController {

    @Autowired
    private CreditLimitTrackerService service;

    @GetMapping("/process-data")
    @ResponseBody
    public String processData()  {
        return this.service.showConflicts();
    }
}
