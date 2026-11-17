package test;
import main.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParallelMonteCarloPiTest {

    @Test
    @DisplayName("Коректно парсить єдиний валідний аргумент кількості потоків")
    void shouldParseValidThreadArgument() {
        assertEquals(8, ParallelMonteCarloPi.parseThreadCount(new String[]{"8"}));
    }

    @Test
    @DisplayName("Викидає виняток, якщо аргументів не 1 або число некоректне")
    void shouldRejectInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.parseThreadCount(new String[]{}));
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.parseThreadCount(new String[]{"4", "1000"}));
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.parseThreadCount(new String[]{"0"}));
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.parseThreadCount(new String[]{"-2"}));
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.parseThreadCount(new String[]{"abc"}));
    }

    @Test
    @DisplayName("Форматує консольний вивід суворо за шаблоном умови")
    void shouldFormatConsoleOutputAccordingToSpecification() {
        SimulationResult result = new SimulationResult(3.14221, 8, 1_000_000_000L, 12.83);
        String expected = String.join(
                System.lineSeparator(),
                "PI is 3.14221",
                "THREADS 8",
                "ITERATIONS 1,000,000,000",
                "TIME 12.83ms"
        );

        assertEquals(expected, result.formatForConsole());
    }
}