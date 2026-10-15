public class LinkedHashSet {
    private final MyLinkedList list = new MyLinkedList();

    public boolean add(Object e) {
        if (e == null) throw new NullPointerException("Element cannot be null");
        if (list.indexOf(e) != -1) return false; // вже існує
        list.add(e);
        return true;
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