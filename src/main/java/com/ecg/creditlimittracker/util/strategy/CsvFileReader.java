package com.ecg.creditlimittracker.util.strategy;

import com.ecg.creditlimittracker.model.CreditLimitModel;
import com.ecg.creditlimittracker.util.Constants;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.*;

public class CsvFileReader implements FileReadingStrategy{

    @Override
    public Map<String, CreditLimitModel> readFileData(String fileName) {
        List<CreditLimitModel> beans = new ArrayList<>(); // TODO : Remove beans
        Map<String, CreditLimitModel> map = new HashMap<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build()) {
            String[] lineInArray;

            while ((lineInArray = reader.readNext()) != null) {
                Arrays.stream(lineInArray).forEach(x -> System.out.print(x + " : "));
                CreditLimitModel bean = new CreditLimitModel();
                for (int i = 0; i < lineInArray.length; i++) {
                    bean.setName(lineInArray[0]);
                    bean.setAddress(lineInArray[1]);
                    bean.setPostcode(lineInArray[2]);
                    bean.setPhoneNumber(lineInArray[3]);
                    bean.setCreditLimit(Double.valueOf(lineInArray[4]));
                    bean.setDob(Constants.CSV_DATE_FORMAT.parse(lineInArray[5]));
                    map.put(bean.getPhoneNumber(), bean);
                }
                beans.add(bean);
            }

            System.out.println("SIZE: " + beans.size());
            beans.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("READING ERROR : " + e);
        }
        return map;
    }
}
