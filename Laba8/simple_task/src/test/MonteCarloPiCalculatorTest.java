package test;
import main.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonteCarloPiCalculatorTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    @DisplayName("Обчислює наближене значення Пі в межах похибки для різної кількості потоків")
    void shouldApproximatePiAccuratelyAcrossThreadCounts(int threads) {
        MonteCarloPiCalculator calculator = new MonteCarloPiCalculator(999L);
        long iterations = 2_000_003L; // навмисно не ділиться націло на кількість потоків

        SimulationResult result = calculator.calculate(threads, iterations);

        assertEquals(Math.PI, result.piEstimate(), 0.01);
        assertEquals(threads, result.threads());
        assertEquals(iterations, result.iterations());
        assertTrue(result.elapsedMillis() >= 0.0);
    }

    @Test
    @DisplayName("Валідує некоректну кількість потоків та ітерацій")
    void shouldValidateArguments() {
        MonteCarloPiCalculator calculator = new MonteCarloPiCalculator();

        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(0, 1000L));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(-4, 1000L));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(4, 0L));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(4, -100L));
    }
}