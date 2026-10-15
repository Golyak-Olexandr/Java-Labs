import java.util.Arrays;

public class MyArrayList implements MyList, RandomAccess {
    private Object[] elementData;
    private int size;

    public MyArrayList() {
        this.elementData = new Object[10];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = elementData.length * 2;
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    @Override
    public void add(Object e) {
        ensureCapacity(size + 1);
        elementData[size++] = e;
    }

    @Override
    public void add(int index, Object element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        ensureCapacity(size + c.length);
        System.arraycopy(c, 0, elementData, size, c.length);
        size += c.length;
    }

    @Override
    public void addAll(int index, Object[] c) {
        checkIndexForAdd(index);
        ensureCapacity(size + c.length);
        System.arraycopy(elementData, index, elementData, index + c.length, size - index);
        System.arraycopy(c, 0, elementData, index, c.length);
        size += c.length;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object oldValue = elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        elementData[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null ? elementData[i] == null : o.equals(elementData[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}