package main;

public class ReflectionDemoService {

    private final StringReflectionModifier modifier;
    private final ConsoleStringReader reader;

    public ReflectionDemoService(StringReflectionModifier modifier, ConsoleStringReader reader) {
        if (modifier == null || reader == null) {
            throw new IllegalArgumentException("Dependencies must not be null");
        }
        this.modifier = modifier;
        this.reader = reader;
    }

    public void runLiteralDemo() {
        String literalString = "Початковий строковий літерал";
        String programmaticReplacement = "Змінено програмно через Reflection!";

        System.out.println("=== 1. Рядок як строковий літерал ===");
        System.out.println("До зміни:    \"" + literalString + "\"");

        modifier.modify(literalString, programmaticReplacement);

        System.out.println("Після зміни: \"" + literalString + "\"");
        System.out.println();
    }

    public void runKeyboardInputDemo() {
        System.out.println("=== 2. Рядок, введений з клавіатури ===");
        String userInputString = reader.readLine("Введіть початковий рядок: ");
        String replacementString = reader.readLine("Введіть нове значення для заміни: ");

        System.out.println("До зміни:    \"" + userInputString + "\"");

        modifier.modify(userInputString, replacementString);

        System.out.println("Після зміни: \"" + userInputString + "\"");
    }
}