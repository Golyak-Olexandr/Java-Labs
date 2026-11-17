package main;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

public class StringReflectionModifier {

    public void modify(String target, String newValue) {
        if (target == null || newValue == null) {
            throw new IllegalArgumentException("Target string and new value must not be null");
        }

        try {
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);
            valueField.set(target, valueField.get(newValue));

            copyCoderIfPresent(target, newValue);
            resetCachedHash(target);
        } catch (InaccessibleObjectException e) {
            throw new IllegalStateException(
                    "JVM блокує доступ до приватних полів String. Додай прапорець запуску: --add-opens java.base/java.lang=ALL-UNNAMED",
                    e
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Не вдалося змінити рядок через рефлексію", e);
        }
    }

    private void copyCoderIfPresent(String target, String source) throws IllegalAccessException {
        try {
            Field coderField = String.class.getDeclaredField("coder");
            coderField.setAccessible(true);
            coderField.setByte(target, coderField.getByte(source));
        } catch (NoSuchFieldException ignored) {
            // У Java 8 використовується char[] без поля coder
        }
    }

    private void resetCachedHash(String target) throws IllegalAccessException {
        try {
            Field hashField = String.class.getDeclaredField("hash");
            hashField.setAccessible(true);
            hashField.setInt(target, 0);
        } catch (NoSuchFieldException ignored) {
            // Ігноруємо, якщо поле відсутнє
        }
    }
}