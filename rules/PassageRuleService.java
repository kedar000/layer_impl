package org.examplemodulespringboot.imple_layer_and_model.service.rules;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PassageRuleService {

    public double calculateScore(String text) {

        String lower = text.toLowerCase();

        double score = 0.0;

        // =====================================================
        // RULE 1 → passage instructions
        // =====================================================

        if (
                lower.contains("read the following passage") ||
                lower.contains("study the passage") ||
                lower.contains("answer based on the passage") ||
                lower.contains("read and answer")
        ) {
            score += 0.30;
        }

        // =====================================================
        // RULE 2 → large paragraph size
        // =====================================================

        int wordCount =
                lower.split("\\s+").length;

        if (wordCount >= 120) {
            score += 0.30;
        }

        // =====================================================
        // RULE 3 → multiple mcqs inside same block
        // =====================================================

        int mcqBlocks = countMatches(
                lower,
                "(a\\)|b\\)|c\\)|d\\))"
        );

        if (mcqBlocks >= 8) {
            score += 0.40;
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