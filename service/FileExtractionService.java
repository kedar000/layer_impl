package org.examplemodulespringboot.imple_layer_and_model.service;

import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileExtractionService {

    public List<QuestionData> extractQuestions(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new RuntimeException("Invalid file");
        }

        if (fileName.endsWith(".csv")) {
            return extractFromCSV(file);
        }

        if (fileName.endsWith(".xlsx")) {
            return extractFromExcel(file);
        }

        throw new RuntimeException("Unsupported file format");
    }

    // ================= CSV =================

    private List<QuestionData> extractFromCSV(MultipartFile file) throws Exception {

        List<QuestionData> questions = new ArrayList<>();

        CSVReader reader = new CSVReader(
                new InputStreamReader(file.getInputStream())
        );

        String[] row;

        boolean firstRow = true;

        while ((row = reader.readNext()) != null) {

            if (firstRow) {
                firstRow = false;
                continue;
            }

            if (row.length == 0) continue;

            QuestionData q = new QuestionData();

            q.setQuestion(row[0]);

            if (row.length > 1) {
                try {
                    q.setActualLabel(Integer.parseInt(row[1]));
                } catch (Exception e) {
                    q.setActualLabel(null);
                }
            }

            questions.add(q);
        }

        return questions;
    }

    // ================= EXCEL =================

    private List<QuestionData> extractFromExcel(MultipartFile file) throws Exception {

        List<QuestionData> questions = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);

        boolean firstRow = true;

        for (Row row : sheet) {

            if (firstRow) {
                firstRow = false;
                continue;
            }

            Cell questionCell = row.getCell(0);

            if (questionCell == null) continue;

            QuestionData q = new QuestionData();

            q.setQuestion(questionCell.toString());

            Cell labelCell = row.getCell(1);

            if (labelCell != null) {
                try {
                    q.setActualLabel((int) labelCell.getNumericCellValue());
                } catch (Exception e) {
                    q.setActualLabel(null);
                }
            }

            questions.add(q);
        }

        workbook.close();

        return questions;
    }
}