package com.star.searching.section04;

public class LinearProbingHashST<Key, Value> {

    private int n;
    private int m = 16;

    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {

        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
    }

    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % m;
    }
}
