package test;

import main.Circle;
import main.Rectangle;
import main.Shape;
import main.ShapeAreaComparator;
import main.ShapeColorComparator;
import main.ShapeController;
import main.ShapeView;
import main.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ShapeOperationsTest {

    @Test
    @DisplayName("Коректно обчислює загальну площу та площу за типом фігури")
    void shouldCalculateTotalAndTypeAreasAccurately() {
        Shape[] shapes = {
                new Rectangle("Червоний", 4.0, 5.0), // 20.0
                new Circle("Синій", 2.0),            // 4 * PI
                new Triangle("Зелений", 6.0, 3.0)    // 9.0
        };

        ShapeController controller = new ShapeController(shapes, new ShapeView(), new Scanner("0\n"));

        assertEquals(20.0 + 4 * Math.PI + 9.0, controller.calculateTotalArea(), 1e-6);
        assertEquals(20.0, controller.calculateAreaByShapeType(Rectangle.class), 1e-6);
        assertEquals(4 * Math.PI, controller.calculateAreaByShapeType(Circle.class), 1e-6);
        assertEquals(9.0, controller.calculateAreaByShapeType(Triangle.class), 1e-6);
    }

    @Test
    @DisplayName("Сортує фігури за площею та за кольором")
    void shouldSortShapesByAreaAndColor() {
        Shape small = new Triangle("Чорний", 2.0, 2.0);     // площа 2.0
        Shape medium = new Rectangle("Білий", 3.0, 4.0);    // площа 12.0
        Shape large = new Circle("Зелений", 5.0);           // площа ~78.54

        Shape[] shapes = {large, small, medium};

        Arrays.sort(shapes, new ShapeAreaComparator());
        assertInstanceOf(Triangle.class, shapes[0]);
        assertInstanceOf(Rectangle.class, shapes[1]);
        assertInstanceOf(Circle.class, shapes[2]);

        Arrays.sort(shapes, new ShapeColorComparator());
        assertEquals("Білий", shapes[0].getShapeColor());
        assertEquals("Зелений", shapes[1].getShapeColor());
        assertEquals("Чорний", shapes[2].getShapeColor());
    }
}