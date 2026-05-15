package org.examplemodulespringboot.imple_layer_and_model.dto;

import org.examplemodulespringboot.imple_layer_and_model.model.QuestionType;

public class FinalPredictionResponse {

    private String question;

    // =====================================================
    // BERT OUTPUT
    // =====================================================

    private QuestionType bertPrediction;

    private Double confidence;

    // =====================================================
    // VERIFICATION OUTPUT
    // =====================================================

    private Boolean verified;

    private Double verificationScore;

    private String verificationReason;

    // =====================================================
    // FINAL OUTPUT
    // =====================================================

    private QuestionType finalPrediction;

    // =====================================================
    // GETTERS / SETTERS
    // =====================================================

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

    public String getVerificationReason() {
        return verificationReason;
    }

    public void setVerificationReason(String verificationReason) {
        this.verificationReason = verificationReason;
    }

    public QuestionType getFinalPrediction() {
        return finalPrediction;
    }

    public void setFinalPrediction(QuestionType finalPrediction) {
        this.finalPrediction = finalPrediction;
    }
}