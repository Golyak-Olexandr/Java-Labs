import java.util.Arrays;

public class ShapeController {
    private final Shape[] shapes;
    private final ShapeView view;

    public ShapeController(Shape[] shapes, ShapeView view) {
        this.shapes = shapes;
        this.view = view;
    }

    public void processData() {
        view.printTitle("ПОЧАТКОВИЙ НАБОР ДАНИХ (10+ ФІГУР)");
        view.printShapes(shapes);

        double totalArea = calculateTotalArea();
        view.printTotalArea(totalArea);

        double rectangleArea = calculateAreaByShapeType(Rectangle.class);
        view.printTargetTypeArea(Rectangle.class, rectangleArea);

        double circleArea = calculateAreaByShapeType(Circle.class);
        view.printTargetTypeArea(Circle.class, circleArea);

        Arrays.sort(shapes, new ShapeAreaComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА ЗБІЛЬШЕННЯМ ПЛОЩІ");
        view.printShapes(shapes);

        Arrays.sort(shapes, new ShapeColorComparator());
        view.printTitle("ВПОРАДКОВАНИЙ НАБІР ЗА КОЛЬОРОМ");
        view.printShapes(shapes);
    }

    public double calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    public double calculateAreaByShapeType(Class<? extends Shape> shapeClass) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (shapeClass.isInstance(shape)) {
                sum += shape.calcArea();
            }
        }
        return sum;
    }
}