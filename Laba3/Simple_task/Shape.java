public abstract class Shape implements Drawable {
    private final String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return String.format("Колір: %-10s | Площа: %6.2f", shapeColor, calcArea());
    }

    @Override
    public void draw() {
        System.out.println("Малюємо фігуру: " + toString());
    }
}