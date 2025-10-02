import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {

        // Null data exception
        if (data == null) {
            throw new IllegalArgumentException("Error: null data cannot be added.");
        }

        // Resize backing array
        if (size+1 == backingArray.length) {
            T[] newArr = (T[]) new Comparable[backingArray.length * 2];

            for (int i = 1; i <= size; i++) {
                newArr[i] = backingArray[i];
            }
            backingArray = newArr;

        }

        // Add at the end
        backingArray[++size] = data;    // write, then bubble up from 'size'
        siftUp(size);


    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Error: the Heap is empty.");
        }

        T min = backingArray[1];
        T last = backingArray[size];
        backingArray[size] = null;
        size--;
        if (size > 0) {
            backingArray[1] = last;
            siftDown(1);
        }
        return min;
    }

    /**
     * HELPER METHODS
     */

    private void siftUp(int i) { // If child is smaller, move up
        while (i > 1) {
            int parent = i / 2;
            if (backingArray[i].compareTo(backingArray[parent]) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (2 * i <= size) {
            int left = 2 * i;
            int right = left + 1;
            int smaller = left;

            if (right <= size && backingArray[right].compareTo(backingArray[left]) < 0) smaller = right;
            if (backingArray[smaller].compareTo(backingArray[i]) >= 0) break;
            swap (i, smaller);
            i = smaller;
        }
    }

    private void swap(int i, int parent) {
        T t = backingArray[i];
        backingArray[i] = backingArray[parent];
        backingArray[parent] = t;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}