package test;

import main.Circle;
import main.Rectangle;
import main.Shape;
import main.ShapeFileManager;
import main.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShapeFileManagerTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Зберігає та відновлює фігури з текстового файлу без втрати параметрів")
    void shouldSaveAndLoadShapesCorrectly() throws IOException {
        Path file = tempDir.resolve("shapes_test");
        Shape[] original = {
                new Rectangle("Синій", 3.5, 4.0),
                new Circle("Червоний", 2.5),
                new Triangle("Жовтий", 6.0, 5.0)
        };

        ShapeFileManager.saveShapes(original, file.toString());
        Shape[] loaded = ShapeFileManager.loadShapes(file.toString());

        assertEquals(3, loaded.length);
        assertInstanceOf(Rectangle.class, loaded[0]);
        assertInstanceOf(Circle.class, loaded[1]);
        assertInstanceOf(Triangle.class, loaded[2]);
        assertEquals(original[0].calcArea(), loaded[0].calcArea(), 0.01);
        assertEquals(original[1].calcArea(), loaded[1].calcArea(), 0.01);
        assertEquals(original[2].calcArea(), loaded[2].calcArea(), 0.01);
    }

    @Test
    @DisplayName("Викидає IOException при спробі прочитати неіснуючий файл")
    void shouldThrowExceptionWhenFileDoesNotExist() {
        Path missingFile = tempDir.resolve("non_existent_file");
        assertThrows(IOException.class, () -> ShapeFileManager.loadShapes(missingFile.toString()));
    }
}