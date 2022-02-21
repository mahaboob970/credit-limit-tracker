package com.ecg.creditlimittracker.model;

public class ConflictModel {
    private String fileName;
    private CreditLimitModel record;

    public ConflictModel(String fileName, CreditLimitModel record) {
        this.fileName = fileName;
        this.record = record;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CreditLimitModel getRecord() {
        return record;
    }

    public void setRecord(CreditLimitModel record) {
        this.record = record;
    }
}
