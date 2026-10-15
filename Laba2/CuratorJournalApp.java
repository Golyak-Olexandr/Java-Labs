import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CuratorJournalApp {
    private final List<CuratorRecord> journal = new ArrayList<>();
    private final BufferedReader reader;

    public CuratorJournalApp() {
        // Фікс кодування консолі Windows для кирилиці
        String consoleEncoding = System.getProperty("os.name").toLowerCase().contains("win") ? "cp866" : "UTF-8";
        Charset charset;
        try {
            charset = Charset.forName(consoleEncoding);
        } catch (Exception e) {
            charset = Charset.defaultCharset();
        }
        this.reader = new BufferedReader(new InputStreamReader(System.in, charset));
    }

    public static void main(String[] args) {
        new CuratorJournalApp().run();
    }

    public void run() {
        while (true) {
            System.out.println("\n--- ЖУРНАЛ КУРАТОРА ---");
            System.out.println("1. Додати новий запис");
            System.out.println("2. Показати всі записи");
            System.out.println("3. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = readLine();
            switch (choice) {
                case "1" -> addRecord();
                case "2" -> showJournal();
                case "3" -> {
                    System.out.println("Завершення роботи.");
                    return;
                }
                default -> System.out.println("Некоректний вибір, спробуйте ще раз.");
            }
        }
    }

    private void addRecord() {
        System.out.println("\n--- Введення даних студента ---");

        String lastName = inputValidated("Прізвище (наприклад, Петренко): ", 
                Validator::validateName, "Некоректне прізвище! Без цифр та спецсимволів (/*+_!@ тощо).");

        String firstName = inputValidated("Ім'я (наприклад, Іван): ", 
                Validator::validateName, "Некоректне ім'я! Без цифр та спецсимволів (/*+_!@ тощо).");

        String birthDate = inputValidated("Дата народження (ДД.ММ.РРРР): ", 
                Validator::validateDate, "Некоректна дата! Формат ДД.ММ.РРРР (тільки цифри та крапки).");

        String phone = inputValidated("Телефон (+380XXXXXXXXX): ", 
                Validator::validatePhone, "Некоректний телефон! Формат +380XXXXXXXXX (тільки + та цифри).");

        String address = inputValidated("Домашня адреса: ", 
                Validator::validateAddress, "Заборонено спецсимволи (/*+_!@#$%^&*) в адресі!");

        CuratorRecord record = new CuratorRecord(lastName, firstName, birthDate, phone, address);
        journal.add(record);
        System.out.println("\n[УСПІХ] Запис успішно додано до журналу!");
    }

    private String inputValidated(String prompt, java.util.function.Predicate<String> validator, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = readLine();
            if (validator.test(input)) {
                return input;
            }
            System.out.println("Помилка: " + errorMsg);
        }
    }

    private String readLine() {
        try {
            String line = reader.readLine();
            return line != null ? line.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private void showJournal() {
        System.out.println("\n--- Записи у журналі куратора ---");
        if (journal.isEmpty()) {
            System.out.println("Журнал порожній.");
            return;
        }
        for (int i = 0; i < journal.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, journal.get(i));
        }
    }
}