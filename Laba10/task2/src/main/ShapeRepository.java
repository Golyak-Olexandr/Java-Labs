package main;

import java.util.Random;
import java.util.logging.Logger;

public class ShapeRepository {

    private static final Logger LOGGER = Logger.getLogger(ShapeRepository.class.getName());
    private static final String[] COLORS = {"Червоний", "Синій", "Зелений", "Жовтий", "Чорний", "Білий"};

    public static Shape[] generateDataset(int count) {
        if (count <= 0) {
            LOGGER.warning("Спроба згенерувати набір із некоректною кількістю: " + count);
            throw new IllegalArgumentException("Кількість фігур має бути більше 0");
        }

        LOGGER.info("Початок генерації набору даних із " + count + " фігур.");
        Shape[] shapes = new Shape[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String color = COLORS[random.nextInt(COLORS.length)];
            int shapeType = random.nextInt(3);

            switch (shapeType) {
                case 0 -> shapes[i] = new Rectangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                case 1 -> shapes[i] = new Triangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                case 2 -> shapes[i] = new Circle(color, 1 + random.nextDouble() * 5);
            }
            // Рівень FINEST відсікається реєстратором (рівень FINE) і не потрапляє ні в файл, ні в консоль
            LOGGER.finest("Згенеровано елемент [" + i + "]: " + shapes[i]);
        }

        LOGGER.fine("Генерацію " + count + " фігур успішно завершено.");
        return shapes;
    }
}