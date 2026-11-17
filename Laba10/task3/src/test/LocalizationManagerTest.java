package test;

import main.LocalizationManager;
import main.Rectangle;
import main.Shape;
import main.ShapeController;
import main.ShapeView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalizationManagerTest {

    @AfterEach
    void resetLocaleToUkrainian() {
        LocalizationManager.getInstance().setLocale(Locale.forLanguageTag("uk"));
    }

    @Test
    @DisplayName("Перемикає мову ресурсів між українською, англійською та польською через ResourceBundle")
    void shouldSwitchLanguagesAcrossResourceBundles() {
        LocalizationManager loc = LocalizationManager.getInstance();

        loc.setLocale(Locale.forLanguageTag("uk"));
        assertEquals("0. Вихід", loc.getString("menu.option.0"));

        loc.setLocale(Locale.ENGLISH);
        assertEquals("0. Exit", loc.getString("menu.option.0"));

        loc.setLocale(Locale.forLanguageTag("pl"));
        assertEquals("0. Wyjście", loc.getString("menu.option.0"));
    }

    @Test
    @DisplayName("Пункт меню 9 змінює поточну локаль контролера під час виконання")
    void shouldChangeLocaleThroughMenuOptionNine() {
        Shape[] shapes = {new Rectangle("Blue", 2.0, 3.0)};
        Scanner simulatedInput = new Scanner("9\n2\n0\n");

        ShapeController controller = new ShapeController(shapes, new ShapeView(), simulatedInput);
        controller.processData();

        assertEquals(Locale.ENGLISH, LocalizationManager.getInstance().getCurrentLocale());
        assertTrue(shapes[0].toString().startsWith("Rectangle"));
    }
}