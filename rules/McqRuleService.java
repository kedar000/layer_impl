package org.examplemodulespringboot.imple_layer_and_model.service.rules;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class McqRuleService {

    public double calculateScore(String text) {

        String lower = text.toLowerCase();

        double score = 0.0;

        // =====================================================
        // RULE 1 → exactly 4 options
        // =====================================================

        int optionCount = countMatches(
                lower,
                "(a\\)|b\\)|c\\)|d\\))"
        );

        if (optionCount == 4) {
            score += 0.50;
        }

        // =====================================================
        // RULE 2 → contains all A B C D
        // =====================================================

        boolean hasAllOptions =
                lower.contains("a)") &&
                lower.contains("b)") &&
                lower.contains("c)") &&
                lower.contains("d)");

        if (hasAllOptions) {
            score += 0.30;
        }

        // =====================================================
        // RULE 3 → only one or zero question marks
        // =====================================================

        int questionMarks =
                countMatches(lower, "\\?");

        if (questionMarks <= 1) {
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