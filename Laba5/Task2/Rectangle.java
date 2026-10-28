package Task2;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return String.format("Прямокутник [%-6.1f x %-6.1f] | %s", width, height, super.toString());
    }
}
