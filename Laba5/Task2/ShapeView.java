package Task2;

public class ShapeView {
    public void printMenu() {
        System.out.println("\n=================== МЕНЮ ===================");
        System.out.println("1. Вивести всі фігури");
        System.out.println("2. Обчислити загальну площу");
        System.out.println("3. Обчислити площу за типом фігури");
        System.out.println("4. Сортувати за зростанням площі");
        System.out.println("5. Сортувати за кольором");
        System.out.println("6. Зберегти фігури у файл (.txt)");
        System.out.println("7. Зчитати фігури з файлу (.txt)");
        System.out.println("8. Згенерувати новий набір фігур");
        System.out.println("0. Вихід");
        System.out.print("Виберіть дію: ");
    }

    public void printTitle(String title) {
        System.out.println("\n==================================================================");
        System.out.println(title);
        System.out.println("==================================================================");
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            System.out.println("Набір фігур порожній.");
            return;
        }
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("[%2d] %s%n", (i + 1), shapes[i]);
        }
    }

    public void printTotalArea(double totalArea) {
        System.out.printf("%n Загальна площа всіх фігур: %.2f%n", totalArea);
    }

    public void printTargetTypeArea(Class<? extends Shape> shapeClass, double area) {
        System.out.printf("Загальна площа фігур виду [%s]: %.2f%n", shapeClass.getSimpleName(), area);
    }

    public void printMessage(String message) {
        System.out.println("✅ " + message);
    }

    public void printError(String error) {
        System.out.println("❌ Помилка: " + error);
    }
}