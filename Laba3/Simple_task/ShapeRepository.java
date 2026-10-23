import java.util.Random;

public class ShapeRepository {
    private static final String[] COLORS = {"Червоний", "Синій", "Зелений", "Жовтий", "Чорний", "Білий"};

    public static Shape[] generateDataset(int count) {
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
        }
        return shapes;
    }
}