package main;

import java.util.Random;
import java.util.logging.Logger;

public class ShapeRepository {

    private static final Logger LOGGER = Logger.getLogger(ShapeRepository.class.getName());
    private static final String[] COLOR_KEYS = {
            "color.red", "color.blue", "color.green", "color.yellow", "color.black", "color.white"
    };

    public static Shape[] generateDataset(int count) {
        if (count <= 0) {
            LOGGER.warning("Спроба згенерувати набір із некоректною кількістю: " + count);
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        LOGGER.info("Початок генерації набору даних із " + count + " фігур.");
        LocalizationManager loc = LocalizationManager.getInstance();
        Shape[] shapes = new Shape[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String color = loc.getString(COLOR_KEYS[random.nextInt(COLOR_KEYS.length)]);
            int shapeType = random.nextInt(3);

            switch (shapeType) {
                case 0 -> shapes[i] = new Rectangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                case 1 -> shapes[i] = new Triangle(color, 2 + random.nextDouble() * 8, 2 + random.nextDouble() * 8);
                case 2 -> shapes[i] = new Circle(color, 1 + random.nextDouble() * 5);
            }
            LOGGER.finest("Згенеровано елемент [" + i + "]: " + shapes[i]);
        }

        LOGGER.fine("Генерацію " + count + " фігур успішно завершено.");
        return shapes;
    }
}