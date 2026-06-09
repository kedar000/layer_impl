import java.util.List;

public class TagRemover {

    private static final String TAG_REGEX =
            "</?(b|strong|i|em|u|mark|span|font|sub|sup|del|ins)(\\s+[^>]*)?>";

    public static List<String> removeFormattingTags(List<String> input) {
        return input.stream()
                .map(s -> s.replaceAll(TAG_REGEX, ""))
                .toList();
    }

    public static void main(String[] args) {

        List<String> input = List.of(
                "kedar",
                "<strong>tarun</strong>",
                "<i>somu</i>",
                "<b><u>harshal</u></b>",
                "<span style=\"color:red\">John</span>",
                "<font size=\"4\">Alice</font>",
                "<sub>x</sub>",
                "<sup>2</sup>",
                "<del>deleted</del>",
                "<ins>inserted</ins>",
                "<strong><i><u>Nested Tags</u></i></strong>"
        );

        System.out.println("Input:");
        input.forEach(System.out::println);

        List<String> output = removeFormattingTags(input);

        System.out.println("\nOutput:");
        output.forEach(System.out::println);
    }
}








import java.util.List;

public class TagStripper {

    public static List<String> stripTags(List<String> input) {
        return input.stream()
                .map(s -> s.replaceAll("<[^>]*>", ""))
                .toList();
    }

    public static void main(String[] args) {
        List<String> input = List.of(
                "<strong>kedar</strong>",
                "<b><i>tarun</i></b>",
                "<span style=\"color:red\">somu</span>",
                "<font size=\"4\">harshal</font>",
                "plain text",
                "<a href=\"test\">link</a>"
        );

        List<String> output = stripTags(input);

        System.out.println("Input:");
        input.forEach(System.out::println);

        System.out.println("\nOutput:");
        output.forEach(System.out::println);
    }
}