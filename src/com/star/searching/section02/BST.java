package com.star.searching.section02;

public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {

        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int n;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }
    }

    public int size() {

        return size(root);
    }

    private int size(Node x) {

        if (x == null) {
            return 0;
        } else {
            return x.n;
        }
    }

    public Value get(Key key) {

        return null;

    }

    public void put(Key key, Value value) {

    }
}
