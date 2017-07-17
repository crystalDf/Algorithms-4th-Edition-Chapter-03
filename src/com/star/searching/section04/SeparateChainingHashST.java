package com.star.searching.section04;

import com.star.searching.section01.SequentialSearchST;

public class SeparateChainingHashST<Key, Value> {

    private static final int M = 997;

    private int n;
    private int m;

    private SequentialSearchST<Key, Value>[] sequentialSearchSTS;

    public SeparateChainingHashST() {

        this(M);
    }

    public SeparateChainingHashST(int m) {

        this.m = m;

        sequentialSearchSTS = new SequentialSearchST[m];

        for (int i = 0; i < m; i++) {

            sequentialSearchSTS[i] = new SequentialSearchST<>();
        }
    }

    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % m;
    }

    public Value get(Key key) {

        return sequentialSearchSTS[hash(key)].get(key);
    }

    public void put(Key key, Value value) {

        sequentialSearchSTS[hash(key)].put(key, value);
    }
}
