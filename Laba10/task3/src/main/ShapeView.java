package main;

public class ShapeView {

    private final LocalizationManager loc = LocalizationManager.getInstance();

    public void printMenu() {
        System.out.println(loc.getString("menu.title"));
        System.out.println(loc.getString("menu.option.1"));
        System.out.println(loc.getString("menu.option.2"));
        System.out.println(loc.getString("menu.option.3"));
        System.out.println(loc.getString("menu.option.4"));
        System.out.println(loc.getString("menu.option.5"));
        System.out.println(loc.getString("menu.option.6"));
        System.out.println(loc.getString("menu.option.7"));
        System.out.println(loc.getString("menu.option.8"));
        System.out.println(loc.getString("menu.option.9"));
        System.out.println(loc.getString("menu.option.0"));
        System.out.print(loc.getString("menu.prompt"));
    }

    public void printPrompt(String key) {
        System.out.print(loc.getString(key));
    }

    public void printTitle(String titleKey) {
        System.out.println("\n==================================================================");
        System.out.println(loc.getString(titleKey));
        System.out.println("==================================================================");
    }

    public void printShapes(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            System.out.println(loc.getString("shapes.empty"));
            return;
        }
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("[%2d] %s%n", (i + 1), shapes[i]);
        }
    }

    public void printTotalArea(double totalArea) {
        System.out.print(loc.format("shapes.total_area", totalArea));
    }

    public void printTargetTypeArea(Class<? extends Shape> shapeClass, double area) {
        System.out.print(loc.format("shapes.type_area", shapeClass.getSimpleName(), area));
    }

    public void printMessageKey(String key, Object... args) {
        System.out.println("[OK] " + loc.format(key, args));
    }

    public void printErrorKey(String key, Object... args) {
        String details = loc.format(key, args);
        System.out.println(loc.format("error.prefix", details));
    }
}