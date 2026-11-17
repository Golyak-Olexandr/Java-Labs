package test;

import main.ArraySumRecursiveTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArraySumRecursiveTaskTest {

    @Test
    @DisplayName("Обчислює суму напряму, коли в підмасиві менше 20 елементів")
    void shouldComputeDirectlyBelowThreshold() {
        int[] array = new int[19];
        Arrays.fill(array, 5);

        long sum = ForkJoinPool.commonPool().invoke(new ArraySumRecursiveTask(array));
        assertEquals(95L, sum);
    }

    @Test
    @DisplayName("Рекурсивно ділить масив, коли елементів 20 і більше")
    void shouldSplitAndSumAboveThreshold() {
        int[] array = new int[1_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        long sum = ForkJoinPool.commonPool().invoke(new ArraySumRecursiveTask(array));
        assertEquals(500_500L, sum);
    }

    @Test
    @DisplayName("Перевіряє коректність меж підмасиву та null-масиву")
    void shouldValidateBoundsAndNullArray() {
        int[] sample = {1, 2, 3};

        assertThrows(IllegalArgumentException.class, () -> new ArraySumRecursiveTask(null));
        assertThrows(IllegalArgumentException.class, () -> new ArraySumRecursiveTask(sample, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> new ArraySumRecursiveTask(sample, 0, 4));
        assertThrows(IllegalArgumentException.class, () -> new ArraySumRecursiveTask(sample, 2, 1));
    }
}