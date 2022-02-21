package com.ecg.creditlimittracker.util.strategy;

import com.ecg.creditlimittracker.model.CreditLimitModel;

import java.util.Map;

public class GenericFileReader {

    private Map<String, CreditLimitModel> contentMap;

    private FileReadingStrategy strategy;

    private String fileName;

    public GenericFileReader(FileReadingStrategy strategy, String fileName) {
        this.strategy = strategy;
        this.fileName = fileName;
    }

    public Map<String, CreditLimitModel> getContentMap() {
        return getStrategy().readFileData(getFileName());
    }

    public void setContentMap(Map<String, CreditLimitModel> contentMap) {
        this.contentMap = contentMap;
    }

    public FileReadingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FileReadingStrategy strategy) {
        this.strategy = strategy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
