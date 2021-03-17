package stream_reduce_regex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NeoUTest {
    @Test
    void calculate_should_sum_all_int() {
        final String input = "1,5,8,98,178, 154";

        int result = new Neo().calculate(input);

        assertThat(result).isEqualTo(444);
    }

    @Test
    void calculate_should_sum_all_int_when_delimiter_is_defined() {
        final String input = "\\[delime]\n1delime5delime8delime98delime178delime 154";

        int result = new Neo().calculate(input);

        assertThat(result).isEqualTo(444);
    }

    @Test
    void extract_delimiter_is_a_word() {
        final String input = "\\[delime]\n1delime5delime8delime98delime178delime 154";

        String result = new Neo().extract(input);

        assertThat(result).isEqualTo("delime");
    }

    @Test
    void extract_delimiter_is_pipe() {
        final String input = "\\[|]\n1delime5delime8delime98delime178delime 154";

        String result = new Neo().extract(input);

        assertThat(result).isEqualTo("|");
    }
}