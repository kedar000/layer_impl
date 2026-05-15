package org.examplemodulespringboot.imple_layer_and_model.service;

import org.examplemodulespringboot.imple_layer_and_model.dto.PredictionResult;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionType;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BertService {

    private final RestTemplate restTemplate = new RestTemplate();

    public PredictionResult predict(String question) {

        try {

            String url = "http://localhost:8000/predict";

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> request =
                    Map.of("text", question);

            HttpEntity<Map<String, String>> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            url,
                            entity,
                            Map.class
                    );

            Map body = response.getBody();

            if (body == null) {
                throw new RuntimeException("Empty response from BERT API");
            }

            String label = (String) body.get("label");

            Double confidence =
                    ((Number) body.get("confidence")).doubleValue();

            PredictionResult result = new PredictionResult();

            result.setPredictedType(mapToEnum(label));

            result.setConfidence(confidence);

            return result;

        } catch (Exception e) {

            throw new RuntimeException(
                    "BERT API Failed: " + e.getMessage()
            );
        }
    }

    private QuestionType mapToEnum(String label) {

        return switch (label.toUpperCase()) {

            case "PASSAGE" -> QuestionType.PASSAGE;

            case "MCQ" -> QuestionType.MCQ;

            case "MAIN" -> QuestionType.MAIN;

            case "SUB" -> QuestionType.SUB;

            default -> QuestionType.MAIN;
        };
    }
}