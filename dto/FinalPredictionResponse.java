package com.example.questionvalidator.dto;

import com.example.questionvalidator.model.QuestionType;

public class FinalPredictionResponse {

    private String question;

    private QuestionType bertPrediction;

    private Double confidence;

    private Boolean verified;

    private Double verificationScore;

    private QuestionType finalPrediction;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getBertPrediction() {
        return bertPrediction;
    }

    public void setBertPrediction(QuestionType bertPrediction) {
        this.bertPrediction = bertPrediction;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Double getVerificationScore() {
        return verificationScore;
    }

    public void setVerificationScore(Double verificationScore) {
        this.verificationScore = verificationScore;
    }

    public QuestionType getFinalPrediction() {
        return finalPrediction;
    }

    public void setFinalPrediction(QuestionType finalPrediction) {
        this.finalPrediction = finalPrediction;
    }
}