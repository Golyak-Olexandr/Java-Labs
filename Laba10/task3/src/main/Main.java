package main;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = AppLoggerConfig.configure();
        logger.info("=== Старт програми (Логування: Logger=FINE, File=FINE -> shapes_app.log, Console=WARNING) ===");

        try {
            Shape[] dataset = ShapeRepository.generateDataset(10);
            ShapeView view = new ShapeView();

            ShapeController controller = new ShapeController(dataset, view);
            controller.processData();
        } finally {
            logger.info("=== Роботу програми завершено ===");
            AppLoggerConfig.shutdown();
        }
    }
}