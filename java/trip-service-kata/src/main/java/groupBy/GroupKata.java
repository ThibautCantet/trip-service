package groupBy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class GroupKata {
    public Map<String, Long> execute(List<String> items) {
        final Map<String, Long> collect = items.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()));
        return collect;
    }

    public Map<String, Long> executeWithObjects(List<Fruit> items) {

        final Map<String, Long> collect = items.stream()
                .collect(groupingBy(
                        Fruit::getName,
                        Collectors.summingLong(Fruit::getCalories)));
        return collect;
    }
}
