package com.example.questionvalidator.dto;

public class PredictionResponse {

    private QuestionType predictedType;

    private Double confidence;

    public QuestionType getPredictedType() {
        return predictedType;
    }

    public void setPredictedType(QuestionType predictedType) {
        this.predictedType = predictedType;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}