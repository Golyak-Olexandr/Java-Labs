package test;
import main.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonteCarloWorkerTaskTest {

    @Test
    @DisplayName("Викидає виняток при від'ємній кількості ітерацій")
    void shouldThrowExceptionForNegativeIterations() {
        assertThrows(IllegalArgumentException.class, () -> new MonteCarloWorkerTask(-1L));
    }

    @Test
    @DisplayName("Повертає 0 влучань при 0 ітерацій")
    void shouldReturnZeroHitsForZeroIterations() {
        MonteCarloWorkerTask task = new MonteCarloWorkerTask(0L, 42L);
        assertEquals(0L, task.call());
    }

    @Test
    @DisplayName("Кількість влучань знаходиться в межах [0, iterations] і відтворюється за seed")
    void shouldReturnDeterministicHitsWithinValidRange() {
        long iterations = 100_000L;
        MonteCarloWorkerTask firstRun = new MonteCarloWorkerTask(iterations, 12345L);
        MonteCarloWorkerTask secondRun = new MonteCarloWorkerTask(iterations, 12345L);

        long hits1 = firstRun.call();
        long hits2 = secondRun.call();

        assertTrue(hits1 >= 0 && hits1 <= iterations);
        assertEquals(hits1, hits2);
    }
}