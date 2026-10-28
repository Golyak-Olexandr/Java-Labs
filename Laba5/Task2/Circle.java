package Task2;

public class Circle extends Shape {
    private final double radius;

    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return String.format("Коло       [радіус: %-4.1f]        | %s", radius, super.toString());
    }
}