package org.examplemodulespringboot.imple_layer_and_model.dto;

import org.examplemodulespringboot.imple_layer_and_model.model.QuestionType;

public class VerificationResult {

    private QuestionType verifiedType;

    private Double verificationScore;

    private Boolean verified;

    private String reason;

    public QuestionType getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(QuestionType verifiedType) {
        this.verifiedType = verifiedType;
    }

    public Double getVerificationScore() {
        return verificationScore;
    }

    public void setVerificationScore(Double verificationScore) {
        this.verificationScore = verificationScore;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
