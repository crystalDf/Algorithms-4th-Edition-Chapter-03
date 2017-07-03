package com.star.searching.section01;

public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private Key[] keys;
    private Value[] values;
    private int n;

    public BinarySearchST(int capacity) {

        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }
}
