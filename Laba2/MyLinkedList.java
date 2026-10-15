public class MyLinkedList implements MyList {
    private static class Node {
        Object data;
        Node next;
        Node prev;

        Node(Node prev, Object data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    @Override
    public void add(Object e) {
        Node l = tail;
        Node newNode = new Node(l, e, null);
        tail = newNode;
        if (l == null) head = newNode;
        else l.next = newNode;
        size++;
    }

    @Override
    public void add(int index, Object element) {
        if (index == size) {
            add(element);
            return;
        }
        Node succ = node(index);
        Node pred = succ.prev;
        Node newNode = new Node(pred, element, succ);
        succ.prev = newNode;
        if (pred == null) head = newNode;
        else pred.next = newNode;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        for (Object item : c) add(item);
    }

    @Override
    public void addAll(int index, Object[] c) {
        int i = index;
        for (Object item : c) {
            add(i++, item);
        }
    }

    @Override
    public Object get(int index) {
        return node(index).data;
    }

    @Override
    public Object remove(int index) {
        Node x = node(index);
        Object element = x.data;
        Node next = x.next;
        Node prev = x.prev;

        if (prev == null) head = next;
        else { prev.next = next; x.prev = null; }

        if (next == null) tail = prev;
        else { next.prev = prev; x.next = null; }

        x.data = null;
        size--;
        return element;
    }

    @Override
    public void set(int index, Object element) {
        node(index).data = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node x = head; x != null; x = x.next) {
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
        for (Node x = head; x != null; x = x.next) result[i++] = x.data;
        return result;
    }

    private Node node(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        Node x;
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