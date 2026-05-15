package com.example.questionvalidator.dto;

import java.util.List;

public class VerificationResponse {

    private Boolean valid;

    private Double score;

    private List<String> matchedRules;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getMatchedRules() {
        return matchedRules;
    }

    public void setMatchedRules(List<String> matchedRules) {
        this.matchedRules = matchedRules;
    }
}