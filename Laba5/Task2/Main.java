package Task2;

public class Main {
    public static void main(String[] args) {
        Shape[] dataset = ShapeRepository.generateDataset(10);
        ShapeView view = new ShapeView();

        ShapeController controller = new ShapeController(dataset, view);
        controller.processData();
    }
}