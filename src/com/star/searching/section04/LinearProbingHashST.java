package com.star.searching.section04;

public class LinearProbingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 16;

    private int n;
    private int m;

    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {

        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int m) {

        this.m = m;

        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
    }

    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % m;
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

    public void put(Key key, Value value) {

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

    public Value get(Key key) {

        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }

        return null;
    }
}
