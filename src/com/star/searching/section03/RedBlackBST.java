package com.star.searching.section03;

import com.star.searching.section02.BST;
import edu.princeton.cs.algs4.Queue;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {

        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;
        private boolean color;

        public Node(Key key, Value value, int size, boolean color) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {

        if (x == null) {
            return false;
        }

        return x.color == RED;
    }

    private Node rotateLeft(Node h) {

        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = getSize(h.left) + getSize(h.right) + 1;

        return x;
    }

    private Node rotateRight(Node h) {

        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = getSize(h.left) + getSize(h.right) + 1;

        return x;
    }

    private void flipColors(Node h) {

        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public Value get(Key key) {

        return get(root, key);

    }

    private Value get(Node x, Key key) {

        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) {

        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
        root.color = BLACK;

        assert check();
    }

    private Node put(Node h, Key key, Value value) {

        if (h == null) {
            return new Node(key, value, 1, RED);
        }

        int cmp = key.compareTo(h.key);

        if (cmp < 0) {
            h.left = put(h.left, key, value);
        } else if (cmp > 0) {
            h.right = put(h.right, key, value);
        } else {
            h.value = value;
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }

        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }

        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.size = getSize(h.left) + getSize(h.right) + 1;

        return h;
    }

    public void delete(Key key) {

        root = delete(root, key);

        assert check();
    }

    private Node delete(Node x, Key key) {

        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) {
                return x.right;
            }
            if (x.right == null) {
                return x.left;
            }

            Node t = x;
            x = min(t.right);
            x.left = t.left;
            x.right = deleteMin(t.right);
        }

        x.size = getSize(x.left) + getSize(x.right) + 1;

        return x;
    }

    public void deleteMin() {

        root = deleteMin(root);

        assert check();
    }

    private Node deleteMin(Node x) {

        if (x.left == null) {
            return x.right;
        }

        x.left = deleteMin(x.left);

        x.size = getSize(x.left) + getSize(x.right) + 1;

        return x;
    }

    public void deleteMax() {

        root = deleteMax(root);

        assert check();
    }

    private Node deleteMax(Node x) {

        if (x.right == null) {
            return x.left;
        }

        x.right = deleteMax(x.right);

        x.size = getSize(x.left) + getSize(x.right) + 1;

        return x;
    }

    public boolean contains(Key key) {

        return get(key) != null;
    }

    public boolean isEmpty() {

        return getSize() == 0;
    }

    public int getSize() {

        return getSize(root);
    }

    private int getSize(Node x) {

        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    public int getSize(Key low, Key high) {

        if (low.compareTo(high) > 0) {
            return 0;
        }

        if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
            return rank(high) - rank(low);
        }
    }

    public int height() {

        return height(root);
    }

    private int height(Node x) {

        if (x == null) {
            return -1;
        }

        return Math.max(height(x.left), height(x.right)) + 1;
    }

    public Key min() {

        return min(root).key;
    }

    private Node min(Node x) {

        if (x.left == null) {
            return x;
        }

        return min(x.left);
    }

    public Key max() {

        return max(root).key;
    }

    private Node max(Node x) {

        if (x.right == null) {
            return x;
        }

        return max(x.right);
    }

    public Key floor(Key key) {

        Node x = floor(root, key);

        if (x == null) {
            return null;
        }

        return x.key;
    }

    private Node floor(Node x, Key key) {

        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        }

        if (cmp < 0) {
            return floor(x.left, key);
        }

        Node t = floor(x.right, key);

        if (t != null) {
            return t;
        }

        return x;
    }

    public Key ceiling(Key key) {

        Node x = ceiling(root, key);

        if (x == null) {
            return null;
        }

        return x.key;
    }

    private Node ceiling(Node x, Key key) {

        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        }

        if (cmp > 0) {
            return ceiling(x.right, key);
        }

        Node t = ceiling(x.left, key);

        if (t != null) {
            return t;
        }

        return x;
    }

    public int rank(Key key) {

        return rank(root, key);
    }

    private int rank(Node x, Key key) {

        if (x == null) {
            return 0;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            return rank(x.left, key);
        } else if (cmp > 0) {
            return rank(x.right, key) + getSize(x.left) + 1;
        } else {
            return getSize(x.left);
        }
    }

    public Key select(int k) {

        return select(root, k).key;
    }

    private Node select(Node x, int k) {

        if (x == null) {
            return null;
        }

        int t = getSize(x.left);

        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - (t + 1));
        } else {
            return x;
        }
    }

    public Iterable<Key> keys() {

        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {

        Queue<Key> queue = new Queue<>();

        keys(root, queue, low, high);

        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key low, Key high) {

        if (x == null) {
            return;
        }

        int cmpLow = low.compareTo(x.key);
        int cmpHigh = high.compareTo(x.key);

        if (cmpLow < 0) {
            keys(x.left, queue, low, high);
        }

        if (cmpLow <= 0 && cmpHigh >= 0) {
            queue.enqueue(x.key);
        }

        if (cmpHigh > 0) {
            keys(x.right, queue, low, high);
        }
    }

    public Iterable<Key> levelOrder() {

        Queue<Key> keys = new Queue<>();
        Queue<Node> nodes = new Queue<>();

        nodes.enqueue(root);

        while (!nodes.isEmpty()) {

            Node x = nodes.dequeue();

            if (x != null) {

                keys.enqueue(x.key);
                nodes.enqueue(x.left);
                nodes.enqueue(x.right);
            }
        }

        return keys;
    }

    private boolean check() {

        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    private boolean isBST() {

        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Key min, Key max) {

        if (x == null) {
            return true;
        }

        if (min != null && min.compareTo(x.key) >= 0) {
            return false;
        }

        if (max != null && max.compareTo(x.key) <= 0) {
            return false;
        }

        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    private boolean isSizeConsistent() {

        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {

        if (x == null) {
            return true;
        }

        if (x.size != getSize(x.left) + getSize(x.right) + 1) {
            return false;
        }

        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    private boolean isRankConsistent() {

        for (int i = 0; i < getSize(); i++) {

            if (i != rank(select(i))) {
                return false;
            }
        }

        for (Key key : keys()) {

            if (key.compareTo(select(rank(key))) != 0 ) {
                return false;
            }
        }

        return true;
    }
}
