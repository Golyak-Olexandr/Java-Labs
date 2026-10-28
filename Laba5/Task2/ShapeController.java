package Task2;

import java.util.Arrays;
import java.util.Scanner;

public class ShapeController {
    private Shape[] shapes;
    private final ShapeView view;
    private final Scanner scanner;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this.shapes = shapes;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void processData() {
        boolean running = true;
        while (running) {
            view.printMenu();
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> view.printShapes(shapes);
                    case 2 -> view.printTotalArea(calculateTotalArea());
                    case 3 -> handleTypeArea();
                    case 4 -> sortAndDisplayByArea();
                    case 5 -> sortAndDisplayByColor();
                    case 6 -> handleSaveToFile();
                    case 7 -> handleLoadFromFile();
                    case 8 -> handleGenerateNew();
                    case 0 -> {
                        view.printMessage("Завершення роботи.");
                        running = false;
                    }
                    default -> view.printError("Некоректна опція. Введіть число від 0 до 8.");
                }
            } catch (NumberFormatException e) {
                view.printError("Некоректний формат! Введіть ціле число.");
            } catch (Exception e) {
                view.printError("Незподівана помилка: " + e.getMessage());
            }
        }
    }

    private void handleTypeArea() {
        System.out.print("Виберіть тип (1 - Rectangle, 2 - Circle, 3 - Triangle): ");
        try {
            int typeChoice = Integer.parseInt(scanner.nextLine().trim());
            switch (typeChoice) {
                case 1 -> view.printTargetTypeArea(Rectangle.class, calculateAreaByShapeType(Rectangle.class));
                case 2 -> view.printTargetTypeArea(Circle.class, calculateAreaByShapeType(Circle.class));
                case 3 -> view.printTargetTypeArea(Triangle.class, calculateAreaByShapeType(Triangle.class));
                default -> view.printError("Невідомий тип фігури.");
            }
        } catch (NumberFormatException e) {
            view.printError("Потрібно ввести число.");
        }
    }

    private void sortAndDisplayByArea() {
        Arrays.sort(shapes, new ShapeAreaComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА ЗБІЛЬШЕННЯМ ПЛОЩІ");
        view.printShapes(shapes);
    }

    private void sortAndDisplayByColor() {
        Arrays.sort(shapes, new ShapeColorComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА КОЛЬОРОМ");
        view.printShapes(shapes);
    }

    private void handleSaveToFile() {
        System.out.print("Введіть ім'я файлу: ");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
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
                view.printError("Кількість має бути більше 0.");
                return;
            }
            shapes = ShapeRepository.generateDataset(count);
            view.printMessage("Згенеровано новий набір з " + count + " фігур.");
            view.printShapes(shapes);
        } catch (NumberFormatException e) {
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