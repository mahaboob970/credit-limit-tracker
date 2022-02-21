package com.ecg.creditlimittracker.service;


import com.ecg.creditlimittracker.model.BookModel;
import com.ecg.creditlimittracker.model.ConflictModel;
import com.ecg.creditlimittracker.model.CreditLimitModel;
import com.ecg.creditlimittracker.util.Constants;
import com.ecg.creditlimittracker.util.strategy.FileReadingStrategy;
import com.ecg.creditlimittracker.util.strategy.GenericFileReader;
import com.ecg.creditlimittracker.util.strategy.StrategyFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CreditLimitTrackerServiceImpl implements CreditLimitTrackerService {

    @Override
    public String showConflicts() {
        return this.buildHtmlReport(this.processAllBooks());
    }


    private List<BookModel> readAllBooks() {

        // Filename, <phone-number,Records>
        List<BookModel> bookModelList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Path.of(Constants.DIRECTORY_PATH))) {
            stream.filter(Files::isRegularFile).forEach(x ->
            {
                System.out.println(x);
                if (x.toString().endsWith(".csv")) {
                    FileReadingStrategy fileReadingStrategy = new StrategyFactory().createFileReadingStrategy(Constants.FileType.CSV);
                    BookModel bookModel = new BookModel(x.getFileName().toString(), new GenericFileReader(fileReadingStrategy, x.toString()).getContentMap(), null);
                    bookModelList.add(bookModel);
                } else if (x.toString().endsWith(".prn")) {
                    FileReadingStrategy fileReadingStrategy = new StrategyFactory().createFileReadingStrategy(Constants.FileType.PRN);
                    BookModel bookModel = new BookModel(x.getFileName().toString(), new GenericFileReader(fileReadingStrategy, x.toString()).getContentMap(), null);
                    bookModelList.add(bookModel);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        return bookModelList;

    }

    private List<BookModel> processAllBooks() {

        List<BookModel> bookModelList = this.readAllBooks();

        bookModelList.forEach(firstBookModel -> {
            Map<String, CreditLimitModel> recordLevelMap = firstBookModel.getRecordsMap();
            List<ConflictModel> conflictModelList = new ArrayList<>();
            bookModelList.forEach(secondBookModel -> {
                Map<String, CreditLimitModel> nextLevelMap = secondBookModel.getRecordsMap();
                if (!firstBookModel.getFileName().equals(secondBookModel.getFileName())) {
                    for (String nextLevelMapKey : nextLevelMap.keySet()) {
                        CreditLimitModel nextLevelModel = nextLevelMap.get(nextLevelMapKey);
                        if (recordLevelMap.get(nextLevelMapKey).getCreditLimit().doubleValue() != nextLevelModel.getCreditLimit().doubleValue()) {
                            conflictModelList.add(new ConflictModel(secondBookModel.getFileName(), nextLevelModel));

                        }

                    }
                }
            });
            firstBookModel.setConflictBaseModelList(conflictModelList);
        });

        System.out.println(bookModelList);

        return bookModelList;

    }


    private String buildHtmlReport(List<BookModel> bookModelList){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("</head>");

        bookModelList.forEach(bookModel -> {
            sb.append("<br> File Name : ").append(bookModel.getFileName()).append("<br><br>");
            sb.append("<table border ='solid 1px'>");
            sb.append("<th> Name </th>");
            sb.append("<th> Address </th>");
            sb.append("<th> Postcode </th>");
            sb.append("<th> Phone </th>");
            sb.append("<th> Credit Limit </th>");
            sb.append("<th> Birthday </th>");

            for (CreditLimitModel creditModel : bookModel.getRecordsMap().values()) {
                sb.append("<tr>");
                sb.append("<td> " + creditModel.getName() + " </td>");
                sb.append("<td> " + creditModel.getAddress() + " </td>");
                sb.append("<td> " + creditModel.getPostcode()+ " </td>");
                sb.append("<td> " + creditModel.getPhoneNumber() + " </td>");
                sb.append("<td> " + creditModel.getCreditLimit() + " </td>");
                sb.append("<td> " + creditModel.getDob() + " </td>");
                sb.append("</tr>");
            }
            sb.append("</table></br></br>");
            sb.append("<br> Conflicts : <br><br>");
            sb.append("<table border ='solid 1px'>");
            for (ConflictModel conflictModel : bookModel.getConflictBaseModelList()) {
                sb.append("<tr>");
                CreditLimitModel conflictRecord=conflictModel.getRecord();
                sb.append("<td> " + conflictRecord.getName() + " </td>");
                sb.append("<td> " + conflictRecord.getAddress() + " </td>");
                sb.append("<td> " + conflictRecord.getPostcode()+ " </td>");
                sb.append("<td> " + conflictRecord.getPhoneNumber() + " </td>");
                sb.append("<td> " + conflictRecord.getCreditLimit() + " </td>");
                sb.append("<td> " + conflictRecord.getDob() + " </td>");
                sb.append("<td> File Name : " + conflictModel.getFileName() + " </td>");
                sb.append("</tr>");
            }
            sb.append("</table></br></br>");
            });

        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();

    }




}
