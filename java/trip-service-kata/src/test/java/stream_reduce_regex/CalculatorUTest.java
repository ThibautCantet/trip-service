package stream_reduce_regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorUTest {
    @Test
    public void add_should_sum_all_numbers(){
        String input = "1,5,8,98,178, 154";

        Calculator calculator = new Calculator();

        int result = calculator.add(input);

        assertEquals(444, result);
    }


    @Test
    public void add_should_sum_all_numbers_except_not_valid_number(){
        String input = "1,5,8,,98,fg,178,154";

        Calculator calculator = new Calculator();

        int result = calculator.add(input);

        assertEquals(444, result);
    }



    @Test
    public void add_should_sum_all_numbers_when_delimiter_is_defined_and_is_word(){
        String input = "\\[delim]\n1delim5delim8delim98delim178delim154";

        Calculator calculator = new Calculator();

        int result = calculator.add(input);

        assertEquals(444, result);
    }


    @Test
    public void add_should_sum_all_numbers_when_delimiter_is_defined_and_is_pipe(){
        String input = "\\[|]\n1|5|8|98|178|154";

        Calculator calculator = new Calculator();

        int result = calculator.add(input);

        assertEquals(444, result);
    }
}