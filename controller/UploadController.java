package org.examplemodulespringboot.imple_layer_and_model.controller;

import org.examplemodulespringboot.imple_layer_and_model.dto.*;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionData;
import org.examplemodulespringboot.imple_layer_and_model.service.BertService;
import org.examplemodulespringboot.imple_layer_and_model.service.FileExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private FileExtractionService fileExtractionService;

    @Autowired
    private BertService bertService;

    @PostMapping("/upload")
    public BatchPredictionResponse uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        List<QuestionData> questions =
                fileExtractionService.extractQuestions(file);

        List<FinalPredictionResponse> predictions =
                new ArrayList<>();

        for (QuestionData q : questions) {

            PredictionResult prediction =
                    bertService.predict(q.getQuestion());

            FinalPredictionResponse response =
                    new FinalPredictionResponse();

            response.setQuestion(q.getQuestion());

            response.setBertPrediction(
                    prediction.getPredictedType()
            );

            response.setConfidence(
                    prediction.getConfidence()
            );

            predictions.add(response);
        }

        BatchPredictionResponse finalResponse =
                new BatchPredictionResponse();

        finalResponse.setPredictions(predictions);

        finalResponse.setTotalQuestions(predictions.size());

        return finalResponse;
    }
}