public class LinkedHashSet<E> {
    private final MyLinkedList<E> list = new MyLinkedList<>();

    public boolean add(E e) {
        if (e == null) throw new NullPointerException("Element cannot be null");
        if (list.indexOf(e) != -1) return false;
        list.add(e);
        return true;
    }

    public boolean addAll(MyList<? extends E> c) {
        if (c == null) return false;
        boolean modified = false;
        for (int i = 0; i < c.size(); i++) {
            if (add(c.get(i))) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean remove(Object e) {
        int index = list.indexOf(e);
        if (index == -1) return false;
        list.remove(index);
        return true;
    }

    public boolean contains(Object e) {
        return list.indexOf(e) != -1;
    }

    public int size() {
        return list.size();
    }

    public Object[] toArray() {
        return list.toArray();
    }
}