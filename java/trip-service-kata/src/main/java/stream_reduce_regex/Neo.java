package stream_reduce_regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Neo {
    public int calculate(String input) {
        final String[] tab = input.split("\n");

        String values = input;
        String splitter = ",";

        if (tab.length > 1) {
            //splitter = tab[0];
            //splitter = splitter.replace("\\[", "");
            //splitter = splitter.replace("]", "");
//
            values = tab[1];

            splitter = extract(input);
        }

        final Integer result = Arrays.stream(values.split(splitter))
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
        return result;
    }

    public String extract(String input) {
        Pattern p = Pattern.compile("\\[.*\\]");
        Matcher matcher = p.matcher(input);
        if (matcher.find()) {
            return matcher.group(0).replace("]", "").replace("[", "");
        }
        return null;
    }
}
