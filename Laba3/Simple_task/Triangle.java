public class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public String toString() {
        return String.format("Трикутник  [основа: %-4.1f, h: %-4.1f] | %s", base, height, super.toString());
    }
}