import java.util.Objects;

public class Cache {
    private static class CacheNode {
        Object key;
        Object value;
        long creationTime;
        CacheNode prev, next;

        CacheNode(Object key, Object value) {
            this.key = key;
            this.value = value;
            this.creationTime = System.currentTimeMillis();
        }
    }

    private final int capacity;
    private final long timeToLiveMillis;
    private final MyLinkedList keysOrder = new MyLinkedList();
    private CacheNode head, tail;
    private int size = 0;

    public Cache(int capacity, long timeToLiveMillis) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.timeToLiveMillis = timeToLiveMillis;
    }

    public synchronized void put(Object key, Object value) {
        Objects.requireNonNull(key, "Cache keys must not be null");   // Не дозволяємо null
        Objects.requireNonNull(value, "Cache values must not be null"); // Не дозволяємо null

        // 1. Очистка застарілих записів (Expiry)
        cleanExpired();

        // 2. Видалення старого запису за тим же ключем
        removeKeyIfExists(key);

        // 3. Якщо досягли ліміту — видаляємо найстаріший елемент (Eviction - FIFO policy)
        if (size >= capacity) {
            evictOldest();
        }

        CacheNode node = new CacheNode(key, value);
        addNodeToTail(node);
        size++;
    }

    public synchronized Object get(Object key) {
        Objects.requireNonNull(key, "Cache key cannot be null");

        cleanExpired();

        CacheNode current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private void cleanExpired() {
        long now = System.currentTimeMillis();
        CacheNode current = head;
        while (current != null) {
            CacheNode next = current.next;
            if (now - current.creationTime > timeToLiveMillis) {
                removeNode(current); // Expiry!
                size--;
            }
            current = next;
        }
    }

    private void evictOldest() {
        if (head != null) {
            removeNode(head); // Eviction!
            size--;
        }
    }

    private void removeKeyIfExists(Object key) {
        CacheNode current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                removeNode(current);
                size--;
                break;
            }
            current = current.next;
        }
    }

    private void addNodeToTail(CacheNode node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private void removeNode(CacheNode node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
    }

    public int size() {
        cleanExpired();
        return size;
    }
}