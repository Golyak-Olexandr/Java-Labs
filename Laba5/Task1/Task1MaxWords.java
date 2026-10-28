package Task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Task1MaxWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву файлу(з його розширенням): ");
        String filePath = scanner.nextLine().trim();

        String maxWordsLine = "";
        int maxCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                String[] words = trimmed.split("\\s+");
                if (words.length > maxCount) {
                    maxCount = words.length;
                    maxWordsLine = line;
                }
            }

            System.out.println("\nРезультат:");
            System.out.println("Максимальна кількість слів: " + maxCount);
            System.out.println("Рядок: " + maxWordsLine);

        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}