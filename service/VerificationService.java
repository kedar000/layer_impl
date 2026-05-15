package org.examplemodulespringboot.imple_layer_and_model.service;

import org.examplemodulespringboot.imple_layer_and_model.dto.VerificationResult;
import org.examplemodulespringboot.imple_layer_and_model.model.QuestionType;
import org.examplemodulespringboot.imple_layer_and_model.service.rules.MainQuestionRuleService;
import org.examplemodulespringboot.imple_layer_and_model.service.rules.McqRuleService;
import org.examplemodulespringboot.imple_layer_and_model.service.rules.PassageRuleService;
import org.examplemodulespringboot.imple_layer_and_model.service.rules.SubQuestionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationService {

    @Autowired
    private McqRuleService mcqRuleService;

    @Autowired
    private SubQuestionRuleService subQuestionRuleService;

    @Autowired
    private PassageRuleService passageRuleService;

    @Autowired
    private MainQuestionRuleService mainQuestionRuleService;

    // =====================================================
    // THRESHOLDS
    // =====================================================

    private final Map<QuestionType, Double> thresholds =
            new HashMap<>();

    public VerificationService() {

        thresholds.put(QuestionType.MCQ, 0.70);

        thresholds.put(QuestionType.SUB, 0.65);

        thresholds.put(QuestionType.PASSAGE, 0.60);

        thresholds.put(QuestionType.MAIN, 0.75);
    }

    // =====================================================
    // MAIN VERIFICATION METHOD
    // =====================================================

    public VerificationResult verify(
            String question,
            QuestionType predictedType
    ) {

        VerificationResult result =
                new VerificationResult();

        // =================================================
        // STEP 1 → verify predicted type only
        // =================================================

        double predictedScore =
                getScore(question, predictedType);

        double threshold =
                thresholds.get(predictedType);

        // =================================================
        // STEP 2 → threshold passed
        // =================================================

        if (predictedScore >= threshold) {

            result.setVerified(true);

            result.setVerifiedType(predictedType);

            result.setVerificationScore(predictedScore);

            result.setReason(
                    "Prediction passed verification threshold"
            );

            return result;
        }

        // =================================================
        // STEP 3 → fallback: run all verifications
        // =================================================

        Map<QuestionType, Double> scores =
                new HashMap<>();

        scores.put(
                QuestionType.MCQ,
                mcqRuleService.calculateScore(question)
        );

        scores.put(
                QuestionType.SUB,
                subQuestionRuleService.calculateScore(question)
        );

        scores.put(
                QuestionType.PASSAGE,
                passageRuleService.calculateScore(question)
        );

        scores.put(
                QuestionType.MAIN,
                mainQuestionRuleService.calculateScore(question)
        );

        // =================================================
        // STEP 4 → choose max score
        // =================================================

        QuestionType bestType = predictedType;

        double bestScore = -1;

        for (Map.Entry<QuestionType, Double> entry :
                scores.entrySet()) {

            if (entry.getValue() > bestScore) {

                bestScore = entry.getValue();

                bestType = entry.getKey();
            }
        }

        // =================================================
        // STEP 5 → return corrected result
        // =================================================

        result.setVerified(false);

        result.setVerifiedType(bestType);

        result.setVerificationScore(bestScore);

        result.setReason(
                "Prediction failed threshold, corrected using rule engine"
        );

        return result;
    }

    // =====================================================
    // SCORE ROUTER
    // =====================================================

    private double getScore(
            String question,
            QuestionType type
    ) {

        return switch (type) {

            case MCQ ->
                    mcqRuleService.calculateScore(question);

            case SUB ->
                    subQuestionRuleService.calculateScore(question);

            case PASSAGE ->
                    passageRuleService.calculateScore(question);

            case MAIN ->
                    mainQuestionRuleService.calculateScore(question);
        };
    }
}