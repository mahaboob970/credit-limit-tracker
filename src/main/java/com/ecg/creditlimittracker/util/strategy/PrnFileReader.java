package com.ecg.creditlimittracker.util.strategy;

import com.ecg.creditlimittracker.model.CreditLimitModel;
import com.ecg.creditlimittracker.util.Constants;
import com.ecg.creditlimittracker.util.CreditLimitUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrnFileReader implements FileReadingStrategy{

    @Override
    public Map<String, CreditLimitModel> readFileData(String fileName) {
        BufferedReader br;
        List<CreditLimitModel> beans = new ArrayList<>();
        Map<String, CreditLimitModel> map = new HashMap<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.ISO_8859_1));
            String line;

            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count == 0) {
                    count += 1;
                    continue;
                }
                CreditLimitModel bean = new CreditLimitModel();
                bean.setName(line.substring(0, 16).trim());
                bean.setAddress(line.substring(16, 38).trim());
                bean.setPostcode(line.substring(38, 47).trim());
                bean.setPhoneNumber(line.substring(47, 62).trim());
                bean.setCreditLimit(CreditLimitUtil.convertDollarToCent(line.substring(62, 74).trim()));
                bean.setDob(Constants.PRN_DATE_FORMAT.parse(line.substring(74).trim()));
                beans.add(bean);
                map.put(bean.getPhoneNumber(), bean);

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return map;
    }
}
