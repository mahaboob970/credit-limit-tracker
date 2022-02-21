package com.ecg.creditlimittracker;

import com.ecg.creditlimittracker.service.CreditLimitTrackerService;
import com.ecg.creditlimittracker.service.CreditLimitTrackerServiceImpl;

public class SampleMain {
    public static void main(String[] args) {
        CreditLimitTrackerServiceImpl service = new CreditLimitTrackerServiceImpl();
//        service.processData();
//        service.processAll();
        service.showConflicts();
    }
}
