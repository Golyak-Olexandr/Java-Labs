public class ShapeView {
    public void printTitle(String title) {
        System.out.println("\n==================================================================");
        System.out.println("📌 " + title);
        System.out.println("==================================================================");
    }

    public void printShapes(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("[%2d] %s%n", (i + 1), shapes[i]);
        }
    }

    public void printTotalArea(double totalArea) {
        System.out.printf("%n📐 Загальна площа всіх фігур: %.2f%n", totalArea);
    }

    public void printTargetTypeArea(Class<? extends Shape> shapeClass, double area) {
        System.out.printf("📐 Загальна площа фігур виду [%s]: %.2f%n", shapeClass.getSimpleName(), area);
    }
}