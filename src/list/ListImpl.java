package list;

import exceptions.ItemNotFoundException;

import java.lang.reflect.Array;

public class ListImpl<T extends Comparable<T>> implements MyList<T> {
    private static final int INITIAL_CAPACITY = 10;
    private int capacity = INITIAL_CAPACITY;
    private int size = 0;
    private static final int FACTOR = 2;

    @SuppressWarnings("unchecked")
    private T[] elements = (T[]) Array.newInstance(Comparable.class, INITIAL_CAPACITY);


    @Override
    public T add(T item) {
        if (size == capacity) {
            increaseCapacity();
        }
        elements[size++] = item;
        return item;
    }

    @Override
    public T add(int index, T item) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == capacity) {
            increaseCapacity();
        }
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        size++;
        elements[index] = item;
        return item;
    }

    @Override
    public T set(int index, T item) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index] = item;
    }

    @Override
    public T remove(T item) throws ItemNotFoundException {
        if (!contains(item)) {
            throw new ItemNotFoundException();
        }
        elements[--size] = null;

        if (size * FACTOR <= capacity) {
            increaseCapacity();
        }
        return item;
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T item = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        if (size * FACTOR <= capacity) {
            increaseCapacity();
        }
        return item;
    }

    @Override
    public boolean contains(T item) {
        for (T elem : elements) {
            if (elem.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T item) {
        return size - 1;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        return elements[index];
    }

    @Override
    public boolean equals(MyList<T> otherList) throws NullPointerException {
        if (otherList == null) {
            throw new NullPointerException();
        }

        if (this == otherList) {
            return true;
        }

        if (otherList.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!elements[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        //noinspection unchecked
        elements = (T[]) Array.newInstance(Comparable.class, INITIAL_CAPACITY);
        capacity = INITIAL_CAPACITY;
        size = 0;
    }

    @Override
    public T[] toArray() {
        //noinspection unchecked
        T[] arr = (T[]) Array.newInstance(Comparable.class, size);
        if (size >= 0) System.arraycopy(elements, 0, arr, 0, size);
        return arr;
    }

    private void increaseCapacity() {
        capacity *= FACTOR;
        T[] newElements = (T[]) Array.newInstance(Comparable.class, capacity);
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private void decreaseCapacity() {
        capacity /= FACTOR;
        T[] newElements = (T[]) Array.newInstance(Comparable.class, capacity);
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}
