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
}
