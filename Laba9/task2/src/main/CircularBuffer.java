package main;

public class CircularBuffer<T> {

    private static final class Node<E> {
        private E value;
        private Node<E> next;
    }

    private final int capacity;
    private final int totalNodes;

    private Node<T> headNode;
    private Node<T> tailNode;
    private int headIndex;
    private int tailIndex;

    public CircularBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
        this.totalNodes = capacity + 1;

        Node<T> first = new Node<>();
        Node<T> current = first;
        for (int i = 1; i < totalNodes; i++) {
            Node<T> nextNode = new Node<>();
            current.next = nextNode;
            current = nextNode;
        }
        current.next = first; // замикаємо список у кільце

        this.headNode = first;
        this.tailNode = first;
        this.headIndex = 0;
        this.tailIndex = 0;
    }

    public synchronized void put(T item) throws InterruptedException {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not allowed in CircularBuffer");
        }
        while (isFull()) {
            wait();
        }

        tailNode.value = item;
        tailNode = tailNode.next;
        tailIndex = (tailIndex + 1) % totalNodes;

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }

        T item = headNode.value;
        headNode.value = null;
        headNode = headNode.next;
        headIndex = (headIndex + 1) % totalNodes;

        notifyAll();
        return item;
    }

    public synchronized boolean isEmpty() {
        return headIndex == tailIndex;
    }

    public synchronized boolean isFull() {
        return (tailIndex + 1) % totalNodes == headIndex;
    }

    public int getCapacity() {
        return capacity;
    }

    public synchronized int getHeadIndex() {
        return headIndex;
    }

    public synchronized int getTailIndex() {
        return tailIndex;
    }
}