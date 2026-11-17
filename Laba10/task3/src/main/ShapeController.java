package main;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeController {

    private static final Logger LOGGER = Logger.getLogger(ShapeController.class.getName());

    private Shape[] shapes;
    private final ShapeView view;
    private final Scanner scanner;
    private final LocalizationManager localizationManager;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this(shapes, view, new Scanner(System.in));
    }

    public ShapeController(Shape[] shapes, ShapeView view, Scanner scanner) {
        this.shapes = shapes;
        this.view = view;
        this.scanner = scanner;
        this.localizationManager = LocalizationManager.getInstance();
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
                    case 9 -> handleLanguageSelection();
                    case 0 -> {
                        LOGGER.info("Користувач обрав вихід із програми.");
                        view.printMessageKey("exit.message");
                        running = false;
                    }
                    default -> {
                        LOGGER.warning("Введено неіснуючий пункт меню: " + choice);
                        view.printErrorKey("error.invalid_option");
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Некоректний формат вводу пункту меню (очікувалося ціле число).");
                view.printErrorKey("error.invalid_number");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Несподівана помилка під час виконання команди меню", e);
                view.printErrorKey("error.unexpected", e.getMessage());
            }
        }
    }

    private void handleLanguageSelection() {
        view.printPrompt("language.prompt");
        if (!scanner.hasNextLine()) {
            return;
        }
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> {
                localizationManager.setLocale(Locale.forLanguageTag("uk"));
                view.printMessageKey("language.changed");
            }
            case "2" -> {
                localizationManager.setLocale(Locale.ENGLISH);
                view.printMessageKey("language.changed");
            }
            case "3" -> {
                localizationManager.setLocale(Locale.forLanguageTag("pl"));
                view.printMessageKey("language.changed");
            }
            default -> {
                LOGGER.warning("Обрано неіснуючий варіант мови: '" + choice + "'");
                view.printErrorKey("language.invalid");
            }
        }
    }

    private void handleTypeArea() {
        view.printPrompt("shapes.type_prompt");
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
                    view.printErrorKey("shapes.type_unknown");
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Нечисловий ввід при виборі типу фігури.");
            view.printErrorKey("error.invalid_number");
        }
    }

    private void sortAndDisplayByArea() {
        LOGGER.info("Сортування фігур за зростанням площі.");
        Arrays.sort(shapes, new ShapeAreaComparator());
        view.printTitle("title.sorted_area");
        view.printShapes(shapes);
    }

    private void sortAndDisplayByColor() {
        LOGGER.info("Сортування фігур за кольором.");
        Arrays.sort(shapes, new ShapeColorComparator());
        view.printTitle("title.sorted_color");
        view.printShapes(shapes);
    }

    private void handleSaveToFile() {
        view.printPrompt("file.prompt");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
            LOGGER.warning("Спроба збереження у файл із порожнім ім'ям.");
            view.printErrorKey("file.empty_name");
            return;
        }
        try {
            ShapeFileManager.saveShapes(shapes, fileName);
            view.printMessageKey("file.saved", fileName);
        } catch (Exception e) {
            view.printErrorKey("file.save_error", e.getMessage());
        }
    }

    private void handleLoadFromFile() {
        view.printPrompt("file.prompt");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
            LOGGER.warning("Спроба зчитування з файлу із порожнім ім'ям.");
            view.printErrorKey("file.empty_name");
            return;
        }
        try {
            shapes = ShapeFileManager.loadShapes(fileName);
            view.printMessageKey("file.loaded", fileName);
            view.printShapes(shapes);
        } catch (Exception e) {
            view.printErrorKey("file.load_error", e.getMessage());
        }
    }

    private void handleGenerateNew() {
        view.printPrompt("generate.prompt");
        try {
            int count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) {
                LOGGER.warning("Введено некоректну кількість фігур для генерації: " + count);
                view.printErrorKey("generate.invalid_count");
                return;
            }
            shapes = ShapeRepository.generateDataset(count);
            view.printMessageKey("generate.success", count);
            view.printShapes(shapes);
        } catch (NumberFormatException e) {
            LOGGER.warning("Некоректний формат числа при генерації нового набору.");
            view.printErrorKey("error.invalid_number");
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