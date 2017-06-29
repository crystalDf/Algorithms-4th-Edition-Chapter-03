package com.star.searching.section01;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SequentialSearchST<Key, Value> {

    private int n;
    private Node first;

    private class Node {

        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public Value get(Key key) {

        for (Node x = first; x != null; x = x.next) {

            if (key.equals(x.key)) {

                return x.value;
            }
        }

        return null;
    }

    public void put(Key key, Value value) {

        for (Node x = first; x != null; x = x.next) {

            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }

        first = new Node(key, value, first);

        n++;
    }

    public void delete(Key key) {

        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {

        if (key.equals(x.key)) {
            n--;

            return x.next;
        }

        x.next = delete(x.next, key);

        return x;
    }

    public boolean contains(Key key) {

        return get(key) != null;
    }

    public boolean isEmpty() {

        return n == 0;
    }

    public int size() {

        return n;
    }

    public Iterable<Key> keys() {

        Queue<Key> queue = new Queue<>();

        for (Node x = first; x != null; x = x.next) {

            queue.enqueue(x.key);
        }

        return queue;
    }

    public static void main(String[] args) {

        SequentialSearchST<String, Integer> sequentialSearchST =
                new SequentialSearchST<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            sequentialSearchST.put(array[i], i);
        }

        for (String string : sequentialSearchST.keys()) {

            StdOut.println(string + " " + sequentialSearchST.get(string));
        }
    }
}
