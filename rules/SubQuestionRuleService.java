package org.examplemodulespringboot.imple_layer_and_model.service.rules;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SubQuestionRuleService {

    public double calculateScore(String text) {

        double score = 0.0;

        // =====================================================
        // RULE 1 → sub labels
        // =====================================================

        int alphaLabels = countMatches(
                text,
                "(a\\)|b\\)|c\\)|d\\))"
        );

        int numericLabels = countMatches(
                text,
                "(1\\.|2\\.|3\\.|4\\.)"
        );

        int capitalLabels = countMatches(
                text,
                "(A\\)|B\\)|C\\)|D\\))"
        );

        if (
                alphaLabels >= 3 ||
                numericLabels >= 3 ||
                capitalLabels >= 3
        ) {
            score += 0.50;
        }

        // =====================================================
        // RULE 2 → multiple question marks
        // =====================================================

        int questionMarks =
                countMatches(text, "\\?");

        if (questionMarks >= 2) {
            score += 0.30;
        }

        // =====================================================
        // RULE 3 → multiline structure
        // =====================================================

        int lines = text.split("\n").length;

        if (lines >= 3) {
            score += 0.20;
        }

        return Math.min(score, 1.0);
    }

    private int countMatches(String text, String regex) {

        return (int) Pattern.compile(regex)
                .matcher(text)
                .results()
                .count();
    }
}