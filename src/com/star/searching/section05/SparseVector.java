package com.star.searching.section05;

import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class SparseVector {

    private int dimension;
    private HashMap<Integer, Double> hashMap;

    public SparseVector(int dimension) {

        this.dimension = dimension;
        this.hashMap = new HashMap<>();
    }

    public int getSize() {

        return hashMap.size();
    }

    public int getDimension() {

        return dimension;
    }

    public void put(int i, double x) {

        hashMap.put(i, x);
    }

    public double get(int i) {

        return hashMap.getOrDefault(i, 0.0);
    }

    public double dot(SparseVector that) {

        double sum = 0.0;

        if (this.hashMap.size() <= that.hashMap.size()) {

            for (int i : this.hashMap.keySet()) {

                if (that.hashMap.containsKey(i)) {
                    sum += this.get(i) * that.get(i);
                }
            }
        } else {

            for (int i : that.hashMap.keySet()) {

                if (this.hashMap.containsKey(i)) {
                    sum += this.get(i) * that.get(i);
                }
            }
        }

        return sum;
    }

    public double dot(double[] that) {

        double sum = 0.0;

        for (int i : hashMap.keySet()) {
            sum += this.get(i) * that[i];
        }

        return sum;
    }

    public double magnitude() {

        return Math.sqrt(this.dot(this));
    }

    public SparseVector scale(double alpha) {

        SparseVector c = new SparseVector(dimension);

        for (int i : this.hashMap.keySet()) {

            c.put(i, this.get(i) * alpha);
        }

        return c;
    }

    public SparseVector plus(SparseVector that) {

        SparseVector c = new SparseVector(dimension);

        for (int i : this.hashMap.keySet()) {
            c.put(i, this.get(i));
        }

        for (int i : that.hashMap.keySet()) {
            c.put(i, that.get(i) + c.get(i));
        }

        return c;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i : hashMap.keySet()) {

            stringBuilder.append("(" + i + ", " + hashMap.get(i) + ") ");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);

        a.put(3, 0.50);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.00);
        b.put(3, 0.60);
        b.put(4, 0.90);

        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a dot b = " + a.dot(b));
        StdOut.println("a + b   = " + a.plus(b));
    }
}
