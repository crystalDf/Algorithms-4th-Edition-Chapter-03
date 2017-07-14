package com.star.searching.section03;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

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

    public RedBlackBST() {

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

        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node moveRedLeft(Node h) {

        flipColors(h);

        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }

        return h;
    }

    private Node moveRedRight(Node h) {

        flipColors(h);

        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }

        return h;
    }

    private Node balance(Node h) {

        if (isRed(h.right)) {
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

        if (!contains(key)) {
            return;
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);

        if (!isEmpty()) {
            root.color = BLACK;
        }

        assert check();
    }

    private Node delete(Node h, Key key) {

        if (h == null) {
            return null;
        }

        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }

            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }

            if (key.compareTo(h.key) == 0 && h.right == null) {
                return null;
            }

            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }

            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }

        return balance(h);
    }

    public void deleteMin() {

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMin(root);

        if (!isEmpty()) {
            root.color = BLACK;
        }

        assert check();
    }

    private Node deleteMin(Node h) {

        if (h.left == null) {
            return null;
        }

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);

        return balance(h);
    }

    public void deleteMax() {

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMax(root);

        if (!isEmpty()) {
            root.color = BLACK;
        }

        assert check();
    }

    private Node deleteMax(Node h) {

        if (isRed(h.left)) {
            h = rotateRight(h);
        }

        if (h.right == null) {
            return h.left;
        }

        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);

        return balance(h);
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

        if (isEmpty()) {
            return new Queue<>();
        }

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

    private boolean isRedBlackBST() {

        return isRedBlackBST(root);
    }

    private boolean isRedBlackBST(Node x) {

        if (x == null) {
            return true;
        }

        if (isRed(x.right)) {
            return false;
        }

        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }

        return isRedBlackBST(x.left) && isRedBlackBST(x.right);
    }

    private boolean isBalanced() {

        int black = 0;

        Node x = root;

        while (x != null) {

            if (!isRed(x)) {
                black++;
            }

            x = x.left;
        }

        return isBalanced(root, black);
    }

    private boolean isBalanced(Node x, int black) {

        if (x == null) {
            return black == 0;
        }

        if (!isRed(x)) {
            black--;
        }

        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    public static void main(String[] args) {

        RedBlackBST<String, Integer> redBlackBST = new RedBlackBST<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            redBlackBST.put(array[i], i);
        }

        for (String string : redBlackBST.levelOrder()) {

            StdOut.println(string + " " + redBlackBST.get(string));
        }

        StdOut.println();

        for (String string : redBlackBST.keys()) {

            StdOut.println(string + " " + redBlackBST.get(string));
        }
    }
}
