package Task2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShapeFileManager {
    public static void saveShapes(Shape[] shapes, String fileName) throws IOException {
        String fullPath = fileName.endsWith(".txt") ? fileName : fileName + ".txt";
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
        }
    }

    public static Shape[] loadShapes(String fileName) throws IOException {
        String fullPath = fileName.endsWith(".txt") ? fileName : fileName + ".txt";
        List<Shape> shapeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
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
                }
            }
        }

        return shapeList.toArray(new Shape[0]);
    }
}