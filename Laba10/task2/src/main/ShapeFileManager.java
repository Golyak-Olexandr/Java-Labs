package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeFileManager {

    private static final Logger LOGGER = Logger.getLogger(ShapeFileManager.class.getName());

    public static void saveShapes(Shape[] shapes, String fileName) throws IOException {
        String fullPath = fileName.endsWith(".txt") ? fileName : fileName + ".txt";
        LOGGER.info("Збереження " + shapes.length + " фігур у файл: " + fullPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            for (Shape shape : shapes) {
                if (shape instanceof Circle c) {
                    writer.write(String.format(Locale.US, "Circle;%s;%.2f%n", c.getShapeColor(), c.getRadius()));
                } else if (shape instanceof Rectangle r) {
                    writer.write(String.format(Locale.US, "Rectangle;%s;%.2f;%.2f%n", r.getShapeColor(), r.getWidth(), r.getHeight()));
                } else if (shape instanceof Triangle t) {
                    writer.write(String.format(Locale.US, "Triangle;%s;%.2f;%.2f%n", t.getShapeColor(), t.getBase(), t.getHeight()));
                }
            }
            LOGGER.fine("Запис у файл " + fullPath + " успішно завершено.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Помилка запису фігур у файл: " + fullPath, e);
            throw e;
        }
    }

    public static Shape[] loadShapes(String fileName) throws IOException {
        String fullPath = fileName.endsWith(".txt") ? fileName : fileName + ".txt";
        LOGGER.info("Зчитування фігур із файлу: " + fullPath);
        List<Shape> shapeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length < 3) {
                    LOGGER.warning("Пропущено некоректний рядок #" + lineNumber + " у файлі " + fullPath);
                    continue;
                }

                String type = parts[0];
                String color = parts[1];

                switch (type) {
                    case "Circle" -> {
                        double radius = Double.parseDouble(parts[2]);
                        shapeList.add(new Circle(color, radius));
                    }
                    case "Rectangle" -> {
                        double width = Double.parseDouble(parts[2]);
                        double height = Double.parseDouble(parts[3]);
                        shapeList.add(new Rectangle(color, width, height));
                    }
                    case "Triangle" -> {
                        double base = Double.parseDouble(parts[2]);
                        double height = Double.parseDouble(parts[3]);
                        shapeList.add(new Triangle(color, base, height));
                    }
                    default -> LOGGER.warning("Невідомий тип фігури '" + type + "' у рядку #" + lineNumber);
                }
            }
            LOGGER.fine("Успішно завантажено фігур із файлу " + fullPath + ": " + shapeList.size());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, "Помилка зчитування або парсингу файлу: " + fullPath, e);
            throw new IOException("Помилка обробки файлу " + fullPath + ": " + e.getMessage(), e);
        }

        return shapeList.toArray(new Shape[0]);
    }
}