package com.star.searching.section04;

import com.star.searching.section01.SequentialSearchST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SeparateChainingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int n;
    private int m;

    private SequentialSearchST<Key, Value>[] sequentialSearchSTS;

    public SeparateChainingHashST() {

        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int m) {

        this.m = m;

        sequentialSearchSTS = new SequentialSearchST[m];

        for (int i = 0; i < m; i++) {
            sequentialSearchSTS[i] = new SequentialSearchST<>();
        }
    }

    private void resize(int chains) {

        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);

        for (int i = 0; i < m; i++) {
            for (Key key : sequentialSearchSTS[i].keys()) {
                temp.put(key, sequentialSearchSTS[i].get(key));
            }
        }

        this.m = temp.m;
        this.n = temp.n;
        this.sequentialSearchSTS = temp.sequentialSearchSTS;
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

        return sequentialSearchSTS[hash(key)].get(key);
    }

    public void put(Key key, Value value) {

        if (value == null) {
            delete(key);
            return;
        }

        if (n >= m * 10) {
            resize(m * 2);
        }

        if (!sequentialSearchSTS[hash(key)].contains(key)) {
            n++;
        }

        sequentialSearchSTS[hash(key)].put(key, value);
    }

    public void delete(Key key) {

        if (sequentialSearchSTS[hash(key)].contains(key)) {
            n--;
        }

        sequentialSearchSTS[hash(key)].delete(key);

        if (m > INIT_CAPACITY && n <= m * 2) {
            resize(m / 2);
        }
    }

    public Iterable<Key> keys() {

        Queue<Key> queue = new Queue<>();

        for (int i = 0; i < m; i++) {
            for (Key key : sequentialSearchSTS[i].keys()) {
                queue.enqueue(key);
            }
        }

        return queue;
    }

    public static void main(String[] args) {

        SeparateChainingHashST<String, Integer> separateChainingHashST =
                new SeparateChainingHashST<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            separateChainingHashST.put(array[i], i);
        }

        for (String string : separateChainingHashST.keys()) {

            StdOut.println(string + " " + separateChainingHashST.get(string));
        }
    }
}
