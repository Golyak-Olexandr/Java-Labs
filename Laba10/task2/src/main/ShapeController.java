package main;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeController {

    private static final Logger LOGGER = Logger.getLogger(ShapeController.class.getName());

    private Shape[] shapes;
    private final ShapeView view;
    private final Scanner scanner;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this(shapes, view, new Scanner(System.in));
    }

    public ShapeController(Shape[] shapes, ShapeView view, Scanner scanner) {
        this.shapes = shapes;
        this.view = view;
        this.scanner = scanner;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    public void processData() {
        LOGGER.info("Запуск головного циклу обробки меню.");
        boolean running = true;

        while (running) {
            view.printMenu();
            if (!scanner.hasNextLine()) {
                LOGGER.warning("Вхідний потік закрито, примусове завершення циклу.");
                break;
            }
            try {
                String input = scanner.nextLine().trim();
                LOGGER.fine("Користувач ввів пункт меню: '" + input + "'");
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> {
                        LOGGER.info("Виведення списку всіх фігур (" + shapes.length + " шт.).");
                        view.printShapes(shapes);
                    }
                    case 2 -> {
                        double total = calculateTotalArea();
                        LOGGER.info(String.format("Обчислено загальну площу: %.2f", total));
                        view.printTotalArea(total);
                    }
                    case 3 -> handleTypeArea();
                    case 4 -> sortAndDisplayByArea();
                    case 5 -> sortAndDisplayByColor();
                    case 6 -> handleSaveToFile();
                    case 7 -> handleLoadFromFile();
                    case 8 -> handleGenerateNew();
                    case 0 -> {
                        LOGGER.info("Користувач обрав вихід із програми.");
                        view.printMessage("Завершення роботи.");
                        running = false;
                    }
                    default -> {
                        LOGGER.warning("Введено неіснуючий пункт меню: " + choice);
                        view.printError("Некоректна опція. Введіть число від 0 до 8.");
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Некоректний формат вводу пункту меню (очікувалося ціле число).");
                view.printError("Некоректний формат! Введіть ціле число.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Несподівана помилка під час виконання команди меню", e);
                view.printError("Несподівана помилка: " + e.getMessage());
            }
        }
    }

    private void handleTypeArea() {
        System.out.print("Виберіть тип (1 - Rectangle, 2 - Circle, 3 - Triangle): ");
        try {
            int typeChoice = Integer.parseInt(scanner.nextLine().trim());
            switch (typeChoice) {
                case 1 -> {
                    double area = calculateAreaByShapeType(Rectangle.class);
                    LOGGER.info(String.format("Обчислено площу для Rectangle: %.2f", area));
                    view.printTargetTypeArea(Rectangle.class, area);
                }
                case 2 -> {
                    double area = calculateAreaByShapeType(Circle.class);
                    LOGGER.info(String.format("Обчислено площу для Circle: %.2f", area));
                    view.printTargetTypeArea(Circle.class, area);
                }
                case 3 -> {
                    double area = calculateAreaByShapeType(Triangle.class);
                    LOGGER.info(String.format("Обчислено площу для Triangle: %.2f", area));
                    view.printTargetTypeArea(Triangle.class, area);
                }
                default -> {
                    LOGGER.warning("Обрано невідомий тип фігури: " + typeChoice);
                    view.printError("Невідомий тип фігури.");
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Нечисловий ввід при виборі типу фігури.");
            view.printError("Потрібно ввести число.");
        }
    }

    private void sortAndDisplayByArea() {
        LOGGER.info("Сортування фігур за зростанням площі.");
        Arrays.sort(shapes, new ShapeAreaComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА ЗБІЛЬШЕННЯМ ПЛОЩІ");
        view.printShapes(shapes);
    }

    private void sortAndDisplayByColor() {
        LOGGER.info("Сортування фігур за кольором.");
        Arrays.sort(shapes, new ShapeColorComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА КОЛЬОРОМ");
        view.printShapes(shapes);
    }

    private void handleSaveToFile() {
        System.out.print("Введіть ім'я файлу: ");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
            LOGGER.warning("Спроба збереження у файл із порожнім ім'ям.");
            view.printError("Ім'я файлу не може бути порожнім.");
            return;
        }
        try {
            ShapeFileManager.saveShapes(shapes, fileName);
            view.printMessage("Дані успішно збережено у файл: " + fileName + ".txt");
        } catch (Exception e) {
            view.printError("Не вдалося зберегти у файл: " + e.getMessage());
        }
    }

    private void handleLoadFromFile() {
        System.out.print("Введіть ім'я файлу: ");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
            LOGGER.warning("Спроба зчитування з файлу із порожнім ім'ям.");
            view.printError("Ім'я файлу не може бути порожнім.");
            return;
        }
        try {
            shapes = ShapeFileManager.loadShapes(fileName);
            view.printMessage("Дані успішно зчитано з файлу: " + fileName + ".txt");
            view.printShapes(shapes);
        } catch (Exception e) {
            view.printError("Не вдалося зчитати з файлу: " + e.getMessage());
        }
    }

    private void handleGenerateNew() {
        System.out.print("Введіть кількість фігур: ");
        try {
            int count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) {
                LOGGER.warning("Введено некоректну кількість фігур для генерації: " + count);
                view.printError("Кількість має бути більше 0.");
                return;
            }
            shapes = ShapeRepository.generateDataset(count);
            view.printMessage("Згенеровано новий набір з " + count + " фігур.");
            view.printShapes(shapes);
        } catch (NumberFormatException e) {
            LOGGER.warning("Некоректний формат числа при генерації нового набору.");
            view.printError("Введено некоректне число.");
        }
    }

    public double calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    public double calculateAreaByShapeType(Class<? extends Shape> shapeClass) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (shapeClass.isInstance(shape)) {
                sum += shape.calcArea();
            }
        }
        return sum;
    }
}