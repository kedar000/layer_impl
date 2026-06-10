package com.mettl.questionsUpload.util;

import com.mettl.questionsUpload.entity.Question;
import com.mettl.questionsUpload.entity.QuestionPaperTemplate;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuestionOptionSanitizer {

    /**
     * Removes HTML tags such as:
     * <strong>, <b>, <i>, <mark>, etc.
     */
    private static final Pattern HTML_TAG_PATTERN =
            Pattern.compile("<[^>]+>");

    /**
     * Removes option labels such as:
     * A.
     * B.
     * C)
     * a.
     * b)
     * (A)
     * (b)
     * I.
     * II.
     * III.
     * IV.
     */
    private static final Pattern OPTION_LABEL_PATTERN =
            Pattern.compile(
                    "^\\s*(?:\\(?[A-Za-z]\\)?[.)]?|\\(?[IVXLCDMivxlcdm]+\\)?[.)]?)\\s*"
            );

    /**
     * Sanitizes all options inside QuestionPaperTemplate.
     */
    public static QuestionPaperTemplate sanitizeOptions(
            QuestionPaperTemplate template) {

        if (template == null || template.getQuestions() == null) {
            return template;
        }

        for (Question question : template.getQuestions()) {

            if (question.getOptions() == null) {
                continue;
            }

            List<String> cleanedOptions = question.getOptions()
                    .stream()
                    .map(QuestionOptionSanitizer::cleanOption)
                    .collect(Collectors.toList());

            question.setOptions(cleanedOptions);
        }

        return template;
    }

    /**
     * Converts:
     * <strong>A. 22</strong> -> 22
     * A. <strong>22</strong> -> 22
     * b. 51 years -> 51 years
     * <mark>C) Java</mark> -> Java
     * (D) Python -> Python
     */
    private static String cleanOption(String option) {

        if (option == null || option.isBlank()) {
            return option;
        }

        // Remove HTML tags
        String cleaned =
                HTML_TAG_PATTERN.matcher(option)
                        .replaceAll("");

        // Remove option label
        cleaned =
                OPTION_LABEL_PATTERN.matcher(cleaned)
                        .replaceFirst("");

        return cleaned.trim();
    }
}