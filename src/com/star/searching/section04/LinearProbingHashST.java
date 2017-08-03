package com.star.searching.section04;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class LinearProbingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int n;
    private int m;

    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {

        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {

        this.m = capacity;

        keys = (Key[]) new Object[capacity];
        values = (Value[]) new Object[capacity];
    }

    private void resize(int capacity) {

        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(capacity);

        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }

        keys = temp.keys;
        values = temp.values;
        m = temp.m;
    }

    public boolean contains(Key key) {

        return get(key) != null;
    }

    public boolean isEmpty() {

        return getSize() == 0;
    }

    public int getSize() {

        return n;
    }

    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % m;
    }

    public Value get(Key key) {

        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }

        return null;
    }

    public void put(Key key, Value value) {

        if (value == null) {
            delete(key);

            return;
        }

        if (n >= m / 2) {
            resize(m * 2);
        }

        int i;

        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }

        keys[i] = key;
        values[i] = value;

        n++;
    }

    public void delete(Key key) {

        if (!contains(key)) {
            return;
        }

        int i = hash(key);

        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        keys[i] = null;
        values[i] = null;

        i = (i + 1) % m;

        while (keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valueToRehash = values[i];

            keys[i] = null;
            values[i] = null;

            n--;

            put(keyToRehash, valueToRehash);

            i = (i + 1) % m;
        }

        n--;

        if (n > 0 && n <= m / 8) {
            resize(m / 2);
        }

        assert check();
    }

    public Iterable<Key> keys() {

        Queue<Key> queue = new Queue<>();

        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }

        return queue;
    }

    private boolean check() {

        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                if (get(keys[i]) != values[i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {

        LinearProbingHashST<String, Integer> linearProbingHashST =
                new LinearProbingHashST<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            linearProbingHashST.put(array[i], i);
        }

        for (String string : linearProbingHashST.keys()) {

            StdOut.println(string + " " + linearProbingHashST.get(string));
        }
    }
}
