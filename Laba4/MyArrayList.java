import java.util.Arrays;

public class MyArrayList<E> implements MyList<E>, RandomAccess {
    private Object[] elementData;
    private int size;

    public MyArrayList() {
        this.elementData = new Object[10];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = Math.max(elementData.length * 2, minCapacity);
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    @Override
    public void add(E e) {
        ensureCapacity(size + 1);
        elementData[size++] = e;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public void addAll(E[] c) {
        if (c == null || c.length == 0) return;
        ensureCapacity(size + c.length);
        System.arraycopy(c, 0, elementData, size, c.length);
        size += c.length;
    }

    @Override
    public void addAll(int index, E[] c) {
        checkIndexForAdd(index);
        if (c == null || c.length == 0) return;
        ensureCapacity(size + c.length);
        System.arraycopy(elementData, index, elementData, index + c.length, size - index);
        System.arraycopy(c, 0, elementData, index, c.length);
        size += c.length;
    }

    @Override
    public void addAll(MyList<? extends E> c) {
        if (c == null || c.size() == 0) return;
        ensureCapacity(size + c.size());
        for (int i = 0; i < c.size(); i++) {
            elementData[size++] = c.get(i);
        }
    }

    @Override
    public void addAll(int index, MyList<? extends E> c) {
        checkIndexForAdd(index);
        if (c == null || c.size() == 0) return;
        int numNew = c.size();
        ensureCapacity(size + numNew);
        System.arraycopy(elementData, index, elementData, index + numNew, size - index);
        for (int i = 0; i < numNew; i++) {
            elementData[index + i] = c.get(i);
        }
        size += numNew;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public void set(int index, E element) {
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}