import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final RedBlackTree tree = new RedBlackTree();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> populateRandomUnsorted();
                    case 2 -> populateRandomSorted();
                    case 3 -> insertFromKeyboard();
                    case 4 -> deleteFromKeyboard();
                    case 5 -> displayTreeAndTraversals();
                    case 6 -> searchFromKeyboard();
                    case 7 -> {
                        tree.clear();
                        System.out.println("✅ Дерево очищено.");
                    }
                    case 0 -> {
                        System.out.println("Завершення роботи.");
                        running = false;
                    }
                    default -> System.out.println("❌ Некоректний вибір. Оберіть пункт від 0 до 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Помилка: Введіть ціле число.");
            } catch (Exception e) {
                System.out.println("❌ Помилка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n================ МЕНЮ ЧЕРВОНО-ЧОРНОГО ДЕРЕВА ================");
        System.out.println("1. Заповнити дерево масивом випадкових чисел (довільний порядок)");
        System.out.println("2. Заповнити дерево масивом випадкових чисел (впорядкований порядок)");
        System.out.println("3. Додати елемент з клавіатури");
        System.out.println("4. Видалити елемент");
        System.out.println("5. Відобразити дерево та обходи");
        System.out.println("6. Пошук елемента");
        System.out.println("7. Очистити дерево");
        System.out.println("0. Вихід");
        System.out.print("Виберіть дію: ");
    }

    private static void populateRandomUnsorted() {
        System.out.print("Введіть кількість елементів: ");
        int count = Integer.parseInt(scanner.nextLine().trim());
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = random.nextInt(100);
            tree.insert(array[i]);
        }
        System.out.println("Згенерований масив (довільний порядок): " + Arrays.toString(array));
        System.out.println("✅ Елементи додані у дерево.");
    }

    private static void populateRandomSorted() {
        System.out.print("Введіть кількість елементів: ");
        int count = Integer.parseInt(scanner.nextLine().trim());
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = random.nextInt(100);
        }
        Arrays.sort(array);
        for (int val : array) {
            tree.insert(val);
        }
        System.out.println("Впорядкований масив (за зростанням): " + Arrays.toString(array));
        System.out.println("✅ Елементи додані у дерево.");
    }

    private static void insertFromKeyboard() {
        System.out.print("Введіть ціле число для додавання: ");
        int value = Integer.parseInt(scanner.nextLine().trim());
        tree.insert(value);
        System.out.println("✅ Елемент " + value + " додано.");
    }

    private static void deleteFromKeyboard() {
        System.out.print("Введіть ціле число для видалення: ");
        int value = Integer.parseInt(scanner.nextLine().trim());
        boolean removed = tree.delete(value);
        if (removed) {
            System.out.println("✅ Елемент " + value + " успішно видалено.");
        } else {
            System.out.println("❌ Елемент " + value + " не знайдено в дереві.");
        }
    }

    private static void displayTreeAndTraversals() {
        System.out.println("\n📌 Структура червоно-чорного дерева:");
        tree.printTree();
        System.out.println("\nОбхід In-order (симетричний): " + tree.getInOrderTraversal());
        System.out.println("Обхід Pre-order (прямий):     " + tree.getPreOrderTraversal());
        System.out.println("Обхід Post-order (зворотний): " + tree.getPostOrderTraversal());
    }

    private static void searchFromKeyboard() {
        System.out.print("Введіть ціле число для пошуку: ");
        int value = Integer.parseInt(scanner.nextLine().trim());
        boolean found = tree.contains(value);
        if (found) {
            System.out.println("✅ Елемент " + value + " присутній у дереві.");
        } else {
            System.out.println("❌ Елемент " + value + " відсутній у дереві.");
        }
    }
}