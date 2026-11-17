package main;

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
        return LocalizationManager.getInstance().format("shape.info", shapeColor, calcArea());
    }

    @Override
    public void draw() {
        System.out.println(LocalizationManager.getInstance().format("shape.draw", toString()));
    }
}