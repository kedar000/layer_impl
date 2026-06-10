public import org.jsoup.Jsoup;

import java.util.List;
import java.util.regex.Pattern;

public class QuestionSanitizer {

    private static final Pattern OPTION_PREFIX_PATTERN =
            Pattern.compile(
                    "^(?i)\\s*(?:\\(?[a-z]\\)?[.)]?|\\(?[ivxlcdm]+\\)?[.)]?)\\s+"
            );

    public static QuestionPaperTemplate sanitizeOptions(
            QuestionPaperTemplate template) {

        if (template == null || template.getQuestions() == null) {
            return template;
        }

        for (Question question : template.getQuestions()) {

            List<String> options = question.getOptions();

            if (options == null) {
                continue;
            }

            for (int i = 0; i < options.size(); i++) {

                String option = options.get(i);

                if (option == null) {
                    continue;
                }

                // Remove HTML tags
                String cleaned = Jsoup.parse(option).text();

                // Remove option labels
                cleaned = OPTION_PREFIX_PATTERN.matcher(cleaned)
                        .replaceFirst("");

                cleaned = cleaned.trim();

                options.set(i, cleaned);
            }
        }

        return template;
    }
} {
    
}
