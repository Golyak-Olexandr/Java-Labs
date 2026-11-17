package test;

import main.ForkJoinArraySumCalculator;
import main.RandomArrayGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ForkJoinArraySumCalculatorTest {

    @Test
    @DisplayName("Сума через ForkJoin збігається з послідовною для масиву на 1_000_000 елементів")
    void shouldMatchSequentialSumForMillionElements() {
        RandomArrayGenerator generator = new RandomArrayGenerator(777L);
        int[] array = generator.generate(1_000_000, 0, 100);

        ForkJoinArraySumCalculator calculator = new ForkJoinArraySumCalculator();
        long parallelSum = calculator.calculateSum(array);
        long sequentialSum = calculator.calculateSequentialSum(array);

        assertEquals(sequentialSum, parallelSum);
    }

    @Test
    @DisplayName("Повертає 0 для порожнього масиву")
    void shouldReturnZeroForEmptyArray() {
        ForkJoinArraySumCalculator calculator = new ForkJoinArraySumCalculator();
        assertEquals(0L, calculator.calculateSum(new int[0]));
    }

    @Test
    @DisplayName("Викидає виняток при передачі null")
    void shouldRejectNullArray() {
        ForkJoinArraySumCalculator calculator = new ForkJoinArraySumCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSum(null));
    }
}