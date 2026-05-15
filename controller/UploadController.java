package org.examplemodulespringboot.imple_layer_and_model.controller;

import org.examplemodulespringboot.imple_layer_and_model.dto.ExtractedQuestionsResponse;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionData;
import org.examplemodulespringboot.imple_layer_and_model.service.FileExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private FileExtractionService fileExtractionService;

    @PostMapping("/upload")
    public ExtractedQuestionsResponse uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        List<QuestionData> questions =
                fileExtractionService.extractQuestions(file);

        ExtractedQuestionsResponse response =
                new ExtractedQuestionsResponse();

        response.setQuestions(questions);
        response.setTotalQuestions(questions.size());

        return response;
    }
}