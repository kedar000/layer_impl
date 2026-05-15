package org.examplemodulespringboot.imple_layer_and_model.service;

import org.examplemodulespringboot.imple_layer_and_model.dto.*;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionData;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionType;
import org.examplemodulespringboot.imple_layer_and_model.utils.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationService {

    @Autowired
    private FileExtractionService fileExtractionService;

    @Autowired
    private BertService bertService;

    @Autowired
    private VerificationService verificationService;

    public EvaluationMetrics evaluate(
            MultipartFile file
    ) throws Exception {

        List<QuestionData> questions =
                fileExtractionService.extractQuestions(file);

        int total = questions.size();

        int bertCorrect = 0;

        int hybridCorrect = 0;

        int correctedPredictions = 0;

        int improvedPredictions = 0;

        Map<String, Integer> bertConfusion =
                new HashMap<>();

        Map<String, Integer> hybridConfusion =
                new HashMap<>();

        // =================================================
        // LOOP THROUGH QUESTIONS
        // =================================================

        for (QuestionData q : questions) {

            QuestionType actualType =
                    LabelMapper.fromLabel(
                            q.getActualLabel()
                    );

            // =============================================
            // BERT PREDICTION
            // =============================================

            PredictionResult bertPrediction =
                    bertService.predict(
                            q.getQuestion()
                    );

            QuestionType bertType =
                    bertPrediction.getPredictedType();

            // =============================================
            // VERIFICATION
            // =============================================

            VerificationResult verification =
                    verificationService.verify(
                            q.getQuestion(),
                            bertType
                    );

            QuestionType hybridType =
                    verification.getVerifiedType();

            // =============================================
            // BERT ACCURACY
            // =============================================

            if (bertType == actualType) {
                bertCorrect++;
            }

            // =============================================
            // HYBRID ACCURACY
            // =============================================

            if (hybridType == actualType) {
                hybridCorrect++;
            }

            // =============================================
            // CORRECTION COUNT
            // =============================================

            if (bertType != hybridType) {
                correctedPredictions++;
            }

            // =============================================
            // IMPROVEMENT COUNT
            // =============================================

            if (
                    bertType != actualType &&
                    hybridType == actualType
            ) {
                improvedPredictions++;
            }

            // =============================================
            // CONFUSION MATRIX
            // =============================================

            String bertKey =
                    actualType + " → " + bertType;

            bertConfusion.put(
                    bertKey,
                    bertConfusion.getOrDefault(
                            bertKey,
                            0
                    ) + 1
            );

            String hybridKey =
                    actualType + " → " + hybridType;

            hybridConfusion.put(
                    hybridKey,
                    hybridConfusion.getOrDefault(
                            hybridKey,
                            0
                    ) + 1
            );
        }

        // =================================================
        // BUILD METRICS
        // =================================================

        EvaluationMetrics metrics =
                new EvaluationMetrics();

        metrics.setTotalQuestions(total);

        metrics.setBertCorrect(bertCorrect);

        metrics.setHybridCorrect(hybridCorrect);

        metrics.setCorrectedPredictions(
                correctedPredictions
        );

        metrics.setImprovedPredictions(
                improvedPredictions
        );

        metrics.setBertAccuracy(
                (double) bertCorrect / total
        );

        metrics.setHybridAccuracy(
                (double) hybridCorrect / total
        );

        metrics.setBertConfusionMatrix(
                bertConfusion
        );

        metrics.setHybridConfusionMatrix(
                hybridConfusion
        );

        return metrics;
    }
}