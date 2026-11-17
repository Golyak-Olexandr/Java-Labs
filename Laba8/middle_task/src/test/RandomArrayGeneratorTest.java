package test;

import main.RandomArrayGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomArrayGeneratorTest {

    @Test
    @DisplayName("Генерує масив розміром 1_000_000 зі значеннями в межах [0, 100]")
    void shouldGenerateMillionElementsWithinBounds() {
        RandomArrayGenerator generator = new RandomArrayGenerator(42L);
        int[] result = generator.generate(1_000_000, 0, 100);

        assertEquals(1_000_000, result.length);
        for (int value : result) {
            assertTrue(value >= 0 && value <= 100);
        }
    }

    @Test
    @DisplayName("Повертає ідентичні масиви при однаковому seed")
    void shouldBeDeterministicWithSameSeed() {
        RandomArrayGenerator first = new RandomArrayGenerator(123L);
        RandomArrayGenerator second = new RandomArrayGenerator(123L);

        assertArrayEquals(first.generate(100, 0, 100), second.generate(100, 0, 100));
    }

    @Test
    @DisplayName("Викидає виняток при некоректному розмірі або межах діапазону")
    void shouldThrowExceptionOnInvalidArguments() {
        RandomArrayGenerator generator = new RandomArrayGenerator();

        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1, 0, 100));
        assertThrows(IllegalArgumentException.class, () -> generator.generate(10, 50, 10));
    }
}