package com.ecg.creditlimittracker.util;

import org.springframework.util.StringUtils;

public class CreditLimitUtil {

    public static double convertDollarToCent(String dollar) {
    	if(StringUtils.isEmpty(dollar)) return 0.0;
        return (Double.parseDouble(dollar) / 100);
    }
}
