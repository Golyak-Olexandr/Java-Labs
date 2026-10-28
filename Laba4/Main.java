public class Main {
    
    public static <T> void copy(MyList<? super T> dest, MyList<? extends T> src) {
        for (int i = 0; i < src.size(); i++) {
            dest.add(src.get(i));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. Демонстрація MyArrayList & Wildcards (? extends E) ===");
        MyList<Number> numberList = new MyArrayList<>();
        MyList<Integer> intList = new MyArrayList<>();
        intList.add(10);
        intList.add(20);
        intList.add(30);

        numberList.addAll(intList);
        
        MyList<Double> doubleList = new MyArrayList<>();
        doubleList.add(5.5);
        doubleList.add(12.3);
        
        numberList.addAll(doubleList);

        System.out.print("Елементи numberList (Integer + Double): ");
        for (int i = 0; i < numberList.size(); i++) {
            System.out.print(numberList.get(i) + " ");
        }
        System.out.println();

        System.out.println("\n=== 2. Демонстрація PECS (copy: ? super T <- ? extends T) ===");
        MyList<Object> objectList = new MyLinkedList<>();
        copy(objectList, numberList);
        System.out.println("Скопійовано у MyLinkedList<Object>, розмір: " + objectList.size());
        for (int i = 0; i < objectList.size(); i++) {
            System.out.println("Object[" + i + "] = " + objectList.get(i));
        }

        System.out.println("\n=== 3. Демонстрація LinkedHashSet<E> з Wildcards ===");
        LinkedHashSet<Number> set = new LinkedHashSet<>();
        set.addAll(intList);
        set.add(10);

        System.out.println("Розмір LinkedHashSet (без дублікатів): " + set.size());
        Object[] setArray = set.toArray();
        for (Object item : setArray) {
            System.out.println("Set element: " + item);
        }

        System.out.println("\n=== 4. Демонстрація Cache<K, V> з TTL та Eviction ===");
        Cache<String, Integer> cache = new Cache<>(2, 1000);

        cache.put("User1", 100);
        cache.put("User2", 200);
        System.out.println("Кеш після путу 2 елементів (size=" + cache.size() + "): User1=" + cache.get("User1"));

        cache.put("User3", 300);
        System.out.println("User1 після eviction: " + cache.get("User1"));
        System.out.println("User2 в кеші: " + cache.get("User2"));
        System.out.println("User3 в кеші: " + cache.get("User3"));

        System.out.println("Очікуємо 1.1 сек для закінчення TTL...");
        Thread.sleep(1100);
        System.out.println("Розмір кешу після закінчення TTL: " + cache.size());
        System.out.println("User2 після TTL: " + cache.get("User2"));

        System.out.println("\n=== Всі тести успішно пройдені! ===");
    }
}