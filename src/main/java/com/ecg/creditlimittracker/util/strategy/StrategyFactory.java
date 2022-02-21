package com.ecg.creditlimittracker.util.strategy;

import com.ecg.creditlimittracker.util.Constants;

public class StrategyFactory {

    public FileReadingStrategy createFileReadingStrategy(Constants.FileType fileType) {
        FileReadingStrategy strategy = null;
        if (fileType != null) {
            if (Constants.FileType.CSV.equals(fileType)) {
                strategy = new CsvFileReader();
            } else if (Constants.FileType.PRN.equals(fileType)) {
                strategy = new PrnFileReader();
            } else {
                System.err.println("Unknown/unsupported strategy-type");
            }
        }
        return strategy;
    }
}
