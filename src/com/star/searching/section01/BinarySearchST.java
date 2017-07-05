package com.star.searching.section01;

public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private Key[] keys;
    private Value[] values;
    private int n;

    public BinarySearchST(int capacity) {

        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public Value get(Key key) {

        if (isEmpty()) {

            return null;
        }

        int i = rank(key);

        if (i < n && keys[i].compareTo(key) == 0) {

            return values[i];
        } else {

            return null;
        }
    }

    public void put(Key key, Value value) {

        int i = rank(key);

        if (i < n && keys[i].compareTo(key) == 0) {

            values[i] = value;
            return;
        }

        for (int j = n; j > i; j--) {

            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }

        keys[i] = key;
        values[i] = value;

        n++;
    }

    public void delete(Key key) {

        int i = rank(key);

        for (int j = i; j < n - 1; j++) {

            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }

        n--;
    }

    private int rank(Key key) {

        int low = 0;
        int high = n - 1;

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

    public boolean isEmpty() {

        return n == 0;
    }

    public int size() {

        return n;
    }


}
