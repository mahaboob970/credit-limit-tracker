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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CreditLimitTrackerServiceImpl implements CreditLimitTrackerService {

    @Override
    public void showConflicts() {
        List<BookModel> bookModelList = this.processAllBooks();
    }


    private List<BookModel> readAllBooks() {

        // Filename, <phone-number,Records>
        final Map<String, Map<String, CreditLimitModel>> filesMap = new HashMap<>();
        List<BookModel> bookModelList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Path.of(Constants.DIRECTORY_PATH))) {
            stream.filter(Files::isRegularFile).forEach(x ->
            {
                System.out.println(x);
                if (x.toString().endsWith(".csv")) {
                    FileReadingStrategy fileReadingStrategy = new StrategyFactory().createFileReadingStrategy(Constants.FileType.CSV);
//                    filesMap.put(x.getFileName().toString(), new GenericFileReader(fileReadingStrategy, x.toString()).getContentMap());
                    BookModel bookModel = new BookModel(x.getFileName().toString(), new GenericFileReader(fileReadingStrategy, x.toString()).getContentMap(), null);
                    bookModelList.add(bookModel);
                    // =(readCsvFile(x.toString())); // TODO: Try for Path
                } else if (x.toString().endsWith(".prn")) {
                    FileReadingStrategy fileReadingStrategy = new StrategyFactory().createFileReadingStrategy(Constants.FileType.PRN);
//                    filesMap.put(x.getFileName().toString(), new GenericFileReader(fileReadingStrategy, x.toString()).getContentMap());
//                        prnOriginalMap[0] =(readPrnFile(x.toString()));
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

        Map<String, List<ConflictModel>> conflictModelMap = new HashMap<>();

        bookModelList.stream().forEach(firstBookModel -> {
            Map<String, CreditLimitModel> recordLevelMap = firstBookModel.getRecordsMap();
            List<ConflictModel> conflictModelList = new ArrayList<>();
            bookModelList.stream().forEach(secondBookModel -> {
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





}
