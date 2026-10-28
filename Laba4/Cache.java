import java.util.Objects;

public class Cache<K, V> {
    private static class CacheNode<K, V> {
        K key;
        V value;
        long creationTime;
        CacheNode<K, V> prev, next;

        CacheNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.creationTime = System.currentTimeMillis();
        }
    }

    private final int capacity;
    private final long timeToLiveMillis;
    private CacheNode<K, V> head, tail;
    private int size = 0;

    public Cache(int capacity, long timeToLiveMillis) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.timeToLiveMillis = timeToLiveMillis;
    }

    public synchronized void put(K key, V value) {
        Objects.requireNonNull(key, "Cache keys must not be null");
        Objects.requireNonNull(value, "Cache values must not be null");

        cleanExpired();
        removeKeyIfExists(key);

        if (size >= capacity) {
            evictOldest();
        }

        CacheNode<K, V> node = new CacheNode<>(key, value);
        addNodeToTail(node);
        size++;
    }

    public synchronized V get(Object key) {
        Objects.requireNonNull(key, "Cache key cannot be null");

        cleanExpired();

        CacheNode<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public synchronized void putAll(MyList<? extends CacheEntry<? extends K, ? extends V>> entries) {
        if (entries == null) return;
        for (int i = 0; i < entries.size(); i++) {
            CacheEntry<? extends K, ? extends V> entry = entries.get(i);
            put(entry.key(), entry.value());
        }
    }

    public record CacheEntry<K, V>(K key, V value) {}

    private void cleanExpired() {
        long now = System.currentTimeMillis();
        CacheNode<K, V> current = head;
        while (current != null) {
            CacheNode<K, V> next = current.next;
            if (now - current.creationTime > timeToLiveMillis) {
                removeNode(current);
                size--;
            }
            current = next;
        }
    }

    private void evictOldest() {
        if (head != null) {
            removeNode(head);
            size--;
        }
    }

    private void removeKeyIfExists(Object key) {
        CacheNode<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                removeNode(current);
                size--;
                break;
            }
            current = current.next;
        }
    }

    private void addNodeToTail(CacheNode<K, V> node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private void removeNode(CacheNode<K, V> node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
    }

    public synchronized int size() {
        cleanExpired();
        return size;
    }
}