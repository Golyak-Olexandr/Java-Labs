package test;

import main.StringReflectionModifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringReflectionModifierTest {

    private StringReflectionModifier modifier;

    @BeforeEach
    void setUp() {
        modifier = new StringReflectionModifier();
    }

    @Test
    @DisplayName("Змінює вміст того самого об'єкта String у пам'яті (латиниця)")
    void shouldMutateAsciiStringInPlace() {
        String original = new String("Initial text");
        String referenceCopy = original;

        modifier.modify(original, "Modified via reflection");

        assertSame(referenceCopy, original);
        assertEquals("Modified via reflection", original);
        assertEquals("Modified via reflection", referenceCopy);
    }

    @Test
    @DisplayName("Коректно змінює рядок з кирилицею та різною довжиною")
    void shouldMutateCyrillicAndDifferentLengthStrings() {
        String original = new String("Привіт");

        modifier.modify(original, "Новий довший український рядок!");

        assertEquals("Новий довший український рядок!", original);
    }

    @Test
    @DisplayName("Викидає виняток при передачі null")
    void shouldThrowExceptionWhenArgumentsAreNull() {
        String sample = new String("test");

        assertThrows(IllegalArgumentException.class, () -> modifier.modify(null, "new"));
        assertThrows(IllegalArgumentException.class, () -> modifier.modify(sample, null));
    }
}