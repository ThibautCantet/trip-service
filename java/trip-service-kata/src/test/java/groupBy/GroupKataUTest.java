package groupBy;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GroupKataUTest {

    @Test
    void execute_should_group_by_value() {
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result = new GroupKata().execute(items);

        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("apple", 3L);
        expectedMap.put("banana", 2L);
        expectedMap.put("orange", 1L);
        expectedMap.put("papaya", 1L);
        assertThat(result).isEqualTo(expectedMap);
    }

    @Test
    void execute_should_group_by_value_when_object() {
        List<Fruit> items =
                Arrays.asList(
                        new Fruit("apple", 100),
                        new Fruit("apple", 100),
                        new Fruit("banana", 200),
                        new Fruit("apple", 100),
                        new Fruit("orange", 100),
                        new Fruit("banana", 200),
                        new Fruit("papaya", 300));

        Map<String, Long> result = new GroupKata().executeWithObjects(items);

        Map<String, Long> expectedMap = new HashMap<>();
        expectedMap.put("apple", 300L);
        expectedMap.put("banana", 400L);
        expectedMap.put("orange", 100L);
        expectedMap.put("papaya", 300L);
        assertThat(result).isEqualTo(expectedMap);
    }
}