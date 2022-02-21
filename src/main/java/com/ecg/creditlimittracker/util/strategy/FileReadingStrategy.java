package com.ecg.creditlimittracker.util.strategy;

import com.ecg.creditlimittracker.model.CreditLimitModel;

import java.util.Map;

public interface FileReadingStrategy {

    Map<String, CreditLimitModel> readFileData(String fileName);

}
