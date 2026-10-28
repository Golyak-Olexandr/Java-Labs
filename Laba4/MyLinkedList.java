public class MyLinkedList<E> implements MyList<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E data, Node<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    @Override
    public void add(E e) {
        Node<E> l = tail;
        Node<E> newNode = new Node<>(l, e, null);
        tail = newNode;
        if (l == null) head = newNode;
        else l.next = newNode;
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            add(element);
            return;
        }
        Node<E> succ = node(index);
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, element, succ);
        succ.prev = newNode;
        if (pred == null) head = newNode;
        else pred.next = newNode;
        size++;
    }

    @Override
    public void addAll(E[] c) {
        if (c == null) return;
        for (E item : c) add(item);
    }

    @Override
    public void addAll(int index, E[] c) {
        if (c == null) return;
        int i = index;
        for (E item : c) {
            add(i++, item);
        }
    }

    @Override
    public void addAll(MyList<? extends E> c) {
        if (c == null) return;
        for (int i = 0; i < c.size(); i++) {
            add(c.get(i));
        }
    }

    @Override
    public void addAll(int index, MyList<? extends E> c) {
        if (c == null) return;
        int cur = index;
        for (int i = 0; i < c.size(); i++) {
            add(cur++, c.get(i));
        }
    }

    @Override
    public E get(int index) {
        return node(index).data;
    }

    @Override
    public E remove(int index) {
        Node<E> x = node(index);
        E element = x.data;
        Node<E> next = x.next;
        Node<E> prev = x.prev;

        if (prev == null) head = next;
        else { prev.next = next; x.prev = null; }

        if (next == null) tail = prev;
        else { next.prev = prev; x.next = null; }

        x.data = null;
        size--;
        return element;
    }

    @Override
    public void set(int index, E element) {
        node(index).data = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            if (o == null ? x.data == null : o.equals(x.data)) return index;
            index++;
        }
        return -1;
    }

    @Override
    public int size() { return size; }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = head; x != null; x = x.next) result[i++] = x.data;
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    private Node<E> node(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        Node<E> x;
        if (index < (size >> 1)) {
            x = head;
            for (int i = 0; i < index; i++) x = x.next;
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
        }
        return x;
    }
}