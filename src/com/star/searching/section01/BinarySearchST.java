package com.star.searching.section01;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private static final int INIT_CAPACITY = 2;

    private Key[] keys;
    private Value[] values;
    private int size;

    public BinarySearchST() {

        this(INIT_CAPACITY);
    }

    public BinarySearchST(int capacity) {

        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public Value get(Key key) {

        if (isEmpty()) {
            return null;
        }

        int i = rank(key);

        if (i < size && keys[i].compareTo(key) == 0) {
            return values[i];
        } else {
            return null;
        }
    }

    public void put(Key key, Value value) {

        if (value == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        if (i < size && keys[i].compareTo(key) == 0) {

            values[i] = value;
            return;
        }

        if (size == keys.length) {

            resize(keys.length * 2);
        }

        for (int j = size; j > i; j--) {

            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }

        keys[i] = key;
        values[i] = value;

        size++;
    }

    public void delete(Key key) {

        if (isEmpty()) {
            return;
        }

        int i = rank(key);

        if (i == size || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < size - 1; j++) {

            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }

        size--;

        keys[size] = null;
        values[size] = null;

        if (size > 0 && size == keys.length / 4) {

            resize(keys.length / 2);
        }
    }

    public void deleteMin() {

        if (isEmpty()) {
            return;
        }

        delete(min());
    }

    public void deleteMax() {

        if (isEmpty()) {
            return;
        }

        delete(max());
    }

    public boolean contains(Key key) {

        return get(key) != null;
    }

    public boolean isEmpty() {

        return getSize() == 0;
    }

    public int getSize() {

        return size;
    }

    public int getSize(Key low, Key high) {

        if (low.compareTo(high) > 0) {
            return 0;
        } else if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
            return rank(high) - rank(low);
        }
    }

    private void resize(int capacity) {

        Key[] tempKeys = (Key[]) new Comparable[capacity];
        Value[] tempValues = (Value[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            tempKeys[i] = keys[i];
            tempValues[i] = values[i];
        }

        keys = tempKeys;
        values = tempValues;
    }

    public Key min() {

        return keys[0];
    }

    public Key max() {

        return keys[size - 1];
    }

    public Key floor(Key key) {

        int i = rank(key);

        if (i == 0) {
            return null;
        } else if (i < size && keys[i].compareTo(key) == 0) {
            return keys[i];
        } else {
            return keys[i - 1];
        }
    }

    public Key ceiling(Key key) {

        int i = rank(key);

        if (i == size) {
            return null;
        } else {
            return keys[i];
        }
    }

    private int rank(Key key) {

        int low = 0;
        int high = size - 1;

        while (low <= high) {

            int middle = low + (high - low) / 2;
            int cmp = key.compareTo(keys[middle]);

            if (cmp < 0) {
                high = middle - 1;
            } else if (cmp > 0) {
                low = middle + 1;
            } else {
                return middle;
            }
        }

        return low;
    }

    public Key select(int k) {

        return keys[k];
    }

    public Iterable<Key> keys() {

        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {

        Queue<Key> queue = new Queue<>();

        if (low.compareTo(high) > 0) {

            return queue;
        }

        for (int i = rank(low); i < rank(high); i++) {

            queue.enqueue(keys[i]);
        }

        if (contains(high)) {

            queue.enqueue(keys[rank(high)]);
        }

        return queue;
    }

    private boolean check() {

        return isSorted() && rankCheck();
    }

    private boolean isSorted() {

        for (int i = 1; i < getSize(); i++) {

            if (keys[i].compareTo(keys[i - 1]) < 0) {
                return false;
            }
        }

        return true;
    }

    private boolean rankCheck() {

        for (int i = 0; i < getSize(); i++) {

            if (i != rank(select(i))) {
                return false;
            }
        }

        for (int i = 0; i < getSize(); i++) {

            if (keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        BinarySearchST<String, Integer> binarySearchST =
                new BinarySearchST<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            binarySearchST.put(array[i], i);
        }

        for (String string : binarySearchST.keys()) {

            StdOut.println(string + " " + binarySearchST.get(string));
        }
    }
}
