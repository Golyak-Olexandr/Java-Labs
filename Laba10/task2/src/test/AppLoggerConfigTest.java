package test;

import main.AppLoggerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppLoggerConfigTest {

    @TempDir
    Path tempDir;

    @AfterEach
    void tearDown() {
        AppLoggerConfig.shutdown();
    }

    @Test
    @DisplayName("Записує INFO/FINE/WARNING у файл, але відсікає FINEST за рівнем реєстратора")
    void shouldFilterLogMessagesByLoggerAndHandlerLevels() throws IOException {
        Path logFile = tempDir.resolve("test_app.log");
        Logger logger = AppLoggerConfig.configure(
                logFile.toString(),
                Level.FINE,
                Level.WARNING,
                Level.FINE
        );

        logger.finest("TRACE_MESSAGE_SHOULD_BE_DROPPED");
        logger.fine("FINE_MESSAGE_IN_FILE");
        logger.info("INFO_MESSAGE_IN_FILE");
        logger.warning("WARNING_MESSAGE_EVERYWHERE");

        AppLoggerConfig.shutdown();

        String logContent = Files.readString(logFile, StandardCharsets.UTF_8);

        assertFalse(logContent.contains("TRACE_MESSAGE_SHOULD_BE_DROPPED"));
        assertTrue(logContent.contains("FINE_MESSAGE_IN_FILE"));
        assertTrue(logContent.contains("INFO_MESSAGE_IN_FILE"));
        assertTrue(logContent.contains("WARNING_MESSAGE_EVERYWHERE"));
    }
}