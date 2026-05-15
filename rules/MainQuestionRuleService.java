package org.examplemodulespringboot.imple_layer_and_model.service.rules;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MainQuestionRuleService {

    public double calculateScore(String text) {

        double score = 1.0;

        // =====================================================
        // RULE 1 → should contain only one question mark
        // =====================================================

        int questionMarks =
                countMatches(text, "\\?");

        if (questionMarks > 1) {
            score -= 0.50;
        }

        // =====================================================
        // RULE 2 → should not contain options
        // =====================================================

        int options = countMatches(
                text,
                "(a\\)|b\\)|c\\)|d\\))"
        );

        if (options >= 2) {
            score -= 0.50;
        }

        return Math.max(score, 0.0);
    }

    private int countMatches(String text, String regex) {

        return (int) Pattern.compile(regex)
                .matcher(text)
                .results()
                .count();
    }
}