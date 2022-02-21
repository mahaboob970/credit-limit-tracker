package com.ecg.creditlimittracker.model;

import java.util.List;
import java.util.Map;

public class BookModel {

    private Map<String,CreditLimitModel> recordsMap; // phone number, model
    private List<ConflictModel> conflictModelList;
    private String fileName;


    public BookModel(String fileName, Map<String, CreditLimitModel> recordsMap, List<ConflictModel> conflictModelList) {
        this.fileName = fileName;
        this.recordsMap = recordsMap;
        this.conflictModelList = conflictModelList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, CreditLimitModel> getRecordsMap() {
        return recordsMap;
    }

    public void setRecordsMap(Map<String, CreditLimitModel> recordsMap) {
        this.recordsMap = recordsMap;
    }

    public List<ConflictModel> getConflictBaseModelList() {
        return conflictModelList;
    }

    public void setConflictBaseModelList(List<ConflictModel> conflictModelList) {
        this.conflictModelList = conflictModelList;
    }
}
