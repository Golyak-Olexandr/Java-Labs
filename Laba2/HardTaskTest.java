import java.util.Arrays;

public class HardTaskTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. ТЕСТ MyArrayList ===");
        MyArrayList arrayList = new MyArrayList();
        arrayList.add("Один");
        arrayList.add("Два");
        arrayList.add(1, "Півтора");
        System.out.println("Елементи: " + Arrays.toString(arrayList.toArray()));
        System.out.println("Перевірка RandomAccess: " + (arrayList instanceof RandomAccess));

        System.out.println("\n=== 2. ТЕСТ MyLinkedList ===");
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.add("А");
        linkedList.add("Б");
        linkedList.add("В");
        linkedList.remove(1); // видалили 'Б'
        System.out.println("Елементи: " + Arrays.toString(linkedList.toArray()));

        System.out.println("\n=== 3. ТЕСТ LinkedHashSet ===");
        LinkedHashSet set = new LinkedHashSet();
        set.add("Унікальний");
        set.add("Унікальний"); // Дублікат, не додасться
        set.add("Другий");
        System.out.println("Розмір (має бути 2): " + set.size());
        System.out.println("Вміст: " + Arrays.toString(set.toArray()));

        System.out.println("\n=== 4. ТЕСТ Cache (Expiry & Eviction) ===");
        // Кеш на 2 елементи з часом життя 1500 мс (1.5 сек)
        Cache cache = new Cache(2, 1500);

        cache.put("K1", "V1");
        cache.put("K2", "V2");
        System.out.println("K1 у кеші: " + cache.get("K1"));

        // Перевірка Eviction (витіснення K1 при додаванні K3)
        cache.put("K3", "V3");
        System.out.println("K1 після Eviction (має бути null): " + cache.get("K1"));
        System.out.println("K3 у кеші: " + cache.get("K3"));

        // Перевірка Expiry (чекаємо 2 секунди)
        System.out.println("Чекаємо 2 секунди для перевірки таймауту (Expiry)...");
        Thread.sleep(2000);
        System.out.println("K3 після Expiry (має бути null): " + cache.get("K3"));
    }
}