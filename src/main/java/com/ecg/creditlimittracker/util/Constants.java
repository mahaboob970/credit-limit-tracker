package com.ecg.creditlimittracker.util;


import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {

    public static final String DIRECTORY_PATH = "src/main/resources";


    public enum FileType {
        CSV,
        PRN
    }

    public static SimpleDateFormat CSV_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public static SimpleDateFormat PRN_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
}
