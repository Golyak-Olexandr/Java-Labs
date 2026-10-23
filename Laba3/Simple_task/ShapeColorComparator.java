import java.util.Comparator;

public class ShapeColorComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape o1, Shape o2) {
        return o1.getShapeColor().compareToIgnoreCase(o2.getShapeColor());
    }
}