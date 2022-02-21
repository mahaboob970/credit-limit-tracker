package com.ecg.creditlimittracker.util;


public class CreditLimitUtil {

    public static double convertDollarToCent(String dollar) {
        return (Double.parseDouble(dollar) / 100);
    }
}
