package stream_reduce_regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public int add(String numbers) {
        String separator = ",";

        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(numbers);

        if (matcher.find()) {
            separator = matcher.group(1);
        }

        String[] tab = numbers.split("\\\n");

        if (tab.length > 1) {
            numbers = tab[1];
        }

        String[] splittedNumbers = numbers.split(separator);

        System.out.println(separator);

        return Arrays.asList(splittedNumbers).stream()
                .map(String::trim)
                .filter(this::isNumber)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    private boolean isNumber(String element) {
        try {
            Integer.parseInt(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
