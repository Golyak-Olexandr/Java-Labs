import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class HardTaskUnitTest {

    private MyArrayList arrayList;
    private MyLinkedList linkedList;
    private LinkedHashSet linkedHashSet;

    @BeforeEach
    void setUp() {
        arrayList = new MyArrayList();
        linkedList = new MyLinkedList();
        linkedHashSet = new LinkedHashSet();
    }

    // ==========================================
    // 1. ТЕСТИ ДЛЯ MyArrayList
    // ==========================================

    @Test
    @DisplayName("MyArrayList: Додавання елементів, динамічне розширення та розмір")
    void testArrayListAddAndSize() {
        assertEquals(0, arrayList.size());
        
        // Додаємо елементи, перевіряємо розмір
        for (int i = 1; i <= 15; i++) {
            arrayList.add("Element_" + i);
        }
        
        assertEquals(15, arrayList.size());
        assertEquals("Element_1", arrayList.get(0));
        assertEquals("Element_15", arrayList.get(14));
    }

    @Test
    @DisplayName("MyArrayList: Додавання за індексом у середину та розсув масиву")
    void testArrayListAddAtIndex() {
        arrayList.add("Один");
        arrayList.add("Два");
        arrayList.add(1, "Півтора"); // Вставка в середину

        assertEquals(3, arrayList.size());
        assertEquals("Один", arrayList.get(0));
        assertEquals("Півтора", arrayList.get(1));
        assertEquals("Два", arrayList.get(2));
    }

    @Test
    @DisplayName("MyLinkedList: Видалення з голови, середини та хвоста")
    void testArrayListRemoveAndSet() {
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");

        linkedList.remove(1); // Середина (видаляємо "2")
        assertEquals(2, linkedList.size());
        assertEquals("3", linkedList.get(1));

        linkedList.remove(0); // Голова (видаляємо "1")
        assertEquals(1, linkedList.size());
        assertEquals("3", linkedList.get(0));
    }

    @Test
    @DisplayName("MyArrayList: Перевірка маркерного інтерфейсу RandomAccess")
    void testArrayListRandomAccessMarker() {
        assertTrue(arrayList instanceof RandomAccess, "MyArrayList має реалізовувати RandomAccess");
    }

    @Test
    @DisplayName("MyArrayList: Виключення при виході за межі масиву (IndexOutOfBoundsException)")
    void testArrayListOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(-1));
        
        arrayList.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(5));
    }

    // ==========================================
    // 2. ТЕСТИ ДЛЯ MyLinkedList
    // ==========================================

    @Test
    @DisplayName("MyLinkedList: Базові операції додавання та отримання")
    void testLinkedListAddAndGet() {
        linkedList.add("Перший");
        linkedList.add("Другий");

        assertEquals(2, linkedList.size());
        assertEquals("Перший", linkedList.get(0));
        assertEquals("Другий", linkedList.get(1));
    }

    @Test
    @DisplayName("MyLinkedList: Видалення з голови, середини та хвоста")
    void testLinkedListRemove() {
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");

        assertEquals("2", linkedList.remove(1)); // Середина
        assertEquals(2, linkedList.size());
        assertEquals("3", linkedList.get(1));

        assertEquals("1", linkedList.remove(0)); // Голова
        assertEquals(1, linkedList.size());
        assertEquals("3", linkedList.get(0));
    }

    @Test
    @DisplayName("MyLinkedList: Виключення IndexOutOfBoundsException")
    void testLinkedListOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.get(0));
    }

    // ==========================================
    // 3. ТЕСТИ ДЛЯ LinkedHashSet
    // ==========================================

    @Test
    @DisplayName("LinkedHashSet: Унікальність та збереження порядку вставки")
    void testLinkedHashSetUniquenessAndOrder() {
        assertTrue(linkedHashSet.add("Унікальний"));
        assertFalse(linkedHashSet.add("Унікальний"), "Дублікат НЕ повинен додаватися!");
        assertTrue(linkedHashSet.add("Другий"));

        assertEquals(2, linkedHashSet.size());
        Object[] arr = linkedHashSet.toArray();
        assertEquals("Унікальний", arr[0]);
        assertEquals("Другий", arr[1]);
    }

    @Test
    @DisplayName("LinkedHashSet: Видалення елементів")
    void testLinkedHashSetRemove() {
        linkedHashSet.add("Alpha");
        linkedHashSet.add("Beta");

        assertTrue(linkedHashSet.remove("Alpha"));
        assertFalse(linkedHashSet.remove("Alpha"), "Повторне видалення має повертати false");
        assertEquals(1, linkedHashSet.size());
    }

    @Test
    @DisplayName("LinkedHashSet: Заборона додавання null")
    void testLinkedHashSetNull() {
        assertThrows(NullPointerException.class, () -> linkedHashSet.add(null));
    }

    // ==========================================
    // 4. ТЕСТИ ДЛЯ Cache (Expiry & Eviction)
    // ==========================================

    @Test
    @DisplayName("Cache: NullPointer validation для ключів/значень")
    void testCacheNullValidation() {
        Cache cache = new Cache(3, 5000);
        assertThrows(NullPointerException.class, () -> cache.put(null, "Val"));
        assertThrows(NullPointerException.class, () -> cache.put("Key", null));
    }

    @Test
    @DisplayName("Cache: Перевірка Eviction Policy (витіснення найстарішого запису)")
    void testCacheEvictionPolicy() {
        Cache cache = new Cache(2, 5000);
        cache.put("K1", "V1");
        cache.put("K2", "V2");

        assertEquals("V1", cache.get("K1"));

        // Додаємо 3-й елемент — K1 (найстаріший) повинен витіснитися
        cache.put("K3", "V3");

        assertNull(cache.get("K1"), "K1 має бути витіснений (Evicted)");
        assertEquals("V2", cache.get("K2"));
        assertEquals("V3", cache.get("K3"));
        assertEquals(2, cache.size());
    }

    @Test
    @DisplayName("Cache: Перевірка Expiry Policy (автоматичне видалення по TTL)")
    void testCacheExpiryPolicy() throws InterruptedException {
        Cache cache = new Cache(5, 300);
        cache.put("K3", "V3");

        assertEquals("V3", cache.get("K3"));

        Thread.sleep(400);

        assertNull(cache.get("K3"), "Запис має прострочитися і повернути null");
        assertEquals(0, cache.size());
    }
}