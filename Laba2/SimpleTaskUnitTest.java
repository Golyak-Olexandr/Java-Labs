import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTaskUnitTest {

    // ==========================================
    // 1. ТЕСТИ ДЛЯ Validator (Прізвище / Ім'я)
    // ==========================================

    @ParameterizedTest
    @ValueSource(strings = {"Голяк", "Олександр", "Дем'ян", "Лук'ян", "Жан-Поль", "Петренко", "Франко-Кривобок"})
    @DisplayName("Validator: Коректні імена та прізвища мають проходити")
    void testValidNames(String name) {
        assertTrue(Validator.validateName(name), "Ім'я '" + name + "' повинно бути валідним");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "123", "Тест123", "---", "-Петро", "Петро-", "Петро*", "Петро+Оля", "Тест_Україна"})
    @DisplayName("Validator: Інвалідні імена (цифри, спецсимволи, суцільні дефіси) мають відхилятися")
    void testInvalidNames(String name) {
        assertFalse(Validator.validateName(name), "Ім'я '" + name + "' повинно бути НЕвалідним");
    }

    @Test
    @DisplayName("Validator: Null для імені повертає false")
    void testNullName() {
        assertFalse(Validator.validateName(null));
    }

    // ==========================================
    // 2. ТЕСТИ ДЛЯ Validator (Дата народження)
    // ==========================================

    @ParameterizedTest
    @ValueSource(strings = {"25.07.2007", "01.01.2000", "31.12.1999", "19.03.2003"})
    @DisplayName("Validator: Валідні дати у форматі ДД.ММ.РРРР")
    void testValidDates(String date) {
        assertTrue(Validator.validateDate(date), "Дата '" + date + "' повинна бути валідною");
    }

    @ParameterizedTest
    @ValueSource(strings = {"32.01.2000", "00.05.2000", "15.13.2000", "2007.07.25", "25/07/2007", "25-07-2007", "abc"})
    @DisplayName("Validator: Інвалідні дати (неіснуючі дні/місяці, неправильні роздільники)")
    void testInvalidDates(String date) {
        assertFalse(Validator.validateDate(date), "Дата '" + date + "' повинна бути НЕвалідною");
    }

    // ==========================================
    // 3. ТЕСТИ ДЛЯ Validator (Телефон)
    // ==========================================

    @ParameterizedTest
    @ValueSource(strings = {"+380680050924", "380680050924", "+380630423965", "0680050924", "0630423965"})
    @DisplayName("Validator: Валідні телефони (+380, 380 або 0XXXXXXXXX)")
    void testValidPhones(String phone) {
        assertTrue(Validator.validatePhone(phone), "Телефон '" + phone + "' повинен бути валідним");
    }

    @ParameterizedTest
    @ValueSource(strings = {"+3806800509", "+38068005092455", "068005092", "abc123456789", "+38068005092a"})
    @DisplayName("Validator: Інвалідні телефони (неправильна довжина, букви)")
    void testInvalidPhones(String phone) {
        assertFalse(Validator.validatePhone(phone), "Телефон '" + phone + "' повинен бути НЕвалідним");
    }

    // ==========================================
    // 4. ТЕСТИ ДЛЯ Validator (Адреса)
    // ==========================================

    @ParameterizedTest
    @ValueSource(strings = {"Домбровського 86", "Князів Острозьких 112", "вул. Перемоги, 12", "пр-т Миру 15, кв. 4"})
    @DisplayName("Validator: Валідні адреси")
    void testValidAddresses(String address) {
        assertTrue(Validator.validateAddress(address), "Адреса '" + address + "' повинна бути валідною");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "--", "/*+_!@#$%", "вул. Перемоги *", "Князів + 112"})
    @DisplayName("Validator: Інвалідні адреси (спецсимволи /*+_!@, відсутність букв/цифр)")
    void testInvalidAddresses(String address) {
        assertFalse(Validator.validateAddress(address), "Адреса '" + address + "' повинна бути НЕвалідною");
    }

    // ==========================================
    // 5. ТЕСТИ ДЛЯ CuratorRecord
    // ==========================================

    @Test
    @DisplayName("CuratorRecord: Геттери та коректне формування toString()")
    void testCuratorRecord() {
        CuratorRecord record = new CuratorRecord("Голяк", "Олександр", "25.07.2007", "+380680050924", "Домбровського 86");

        assertEquals("Голяк", record.getLastName());
        assertEquals("Олександр", record.getFirstName());
        assertEquals("25.07.2007", record.getBirthDate());
        assertEquals("+380680050924", record.getPhone());
        assertEquals("Домбровського 86", record.getAddress());

        String str = record.toString();
        assertTrue(str.contains("Голяк"));
        assertTrue(str.contains("Олександр"));
        assertTrue(str.contains("25.07.2007"));
    }
}