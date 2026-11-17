package main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class AppLoggerConfig {

    public static final String ROOT_LOGGER_NAME = "main";
    public static final String DEFAULT_LOG_FILE = "shapes_app.log";

    private AppLoggerConfig() {
    }

    public static Logger configure() {
        return configure(DEFAULT_LOG_FILE, Level.FINE, Level.WARNING, Level.FINE);
    }

    public static Logger configure(
            String logFilePath,
            Level loggerLevel,
            Level consoleLevel,
            Level fileLevel
    ) {
        Logger appLogger = Logger.getLogger(ROOT_LOGGER_NAME);
        appLogger.setUseParentHandlers(false);
        clearHandlers(appLogger);

        appLogger.setLevel(loggerLevel);

        SimpleFormatter formatter = new SimpleFormatter();

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(consoleLevel);
        consoleHandler.setFormatter(formatter);
        try {
            consoleHandler.setEncoding(StandardCharsets.UTF_8.name());
        } catch (Exception ignored) {
        }
        appLogger.addHandler(consoleHandler);

        try {
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setLevel(fileLevel);
            fileHandler.setFormatter(formatter);
            fileHandler.setEncoding(StandardCharsets.UTF_8.name());
            appLogger.addHandler(fileHandler);
        } catch (IOException e) {
            appLogger.log(Level.SEVERE, "Не вдалося створити файл логів: " + logFilePath, e);
        }

        return appLogger;
    }

    public static void shutdown() {
        Logger appLogger = Logger.getLogger(ROOT_LOGGER_NAME);
        clearHandlers(appLogger);
    }

    private static void clearHandlers(Logger logger) {
        for (Handler handler : logger.getHandlers()) {
            handler.flush();
            handler.close();
            logger.removeHandler(handler);
        }
    }
}