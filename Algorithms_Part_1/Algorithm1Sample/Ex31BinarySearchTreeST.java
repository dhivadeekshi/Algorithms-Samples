/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Ex31BinarySearchTreeST<Key extends Comparable<Key>, Value>
        implements SymbolTable<Key, Value> {

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int count;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }

    private Node root = null;

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public void delete(Key key) {
        if (isEmpty()) throw new NoSuchElementException();
        root = delete(root, key);
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Key min() {
        if (isEmpty()) return null;
        Node minNode = root;
        while (minNode.left != null) minNode = minNode.left;
        return minNode.key;
    }

    @Override
    public Key max() {
        if (isEmpty()) return null;
        Node maxNode = root;
        while (maxNode.right != null) maxNode = maxNode.right;
        return maxNode.key;
    }

    @Override
    public Key floor(Key key) {
        return floor(root, key);
    }

    @Override
    public Key ceiling(Key key) {
        return ceiling(root, key);
    }

    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    @Override
    public Key select(int k) {
        return select(root, k);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMax(root);
    }

    @Override
    public int size(Key lo, Key hi) {
        return rank(hi) - rank(lo) + 1;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(root, lo, hi, queue);
        return queue;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        keys(root, queue);
        return queue;
    }

    public Iterable<Value> sorted() {
        Queue<Value> values = new Queue<>();
        sorted(root, values);
        return values;
    }

    public void inOrder() {
        inOrder(root);
    }

    private int size(Node node) {
        return node == null ? 0 : node.count;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value);
        int cmp = node.key.compareTo(key);
        if (cmp > 0) node.left = put(node.left, key, value);
        else if (cmp < 0) node.right = put(node.right, key, value);
        else node.value = value;
        node.count = size(node.right) + size(node.left) + 1;
        return node;
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp > 0) return get(node.left, key);
        else if (cmp < 0) return get(node.right, key);
        return node.value;
    }

    private Key floor(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp > 0) return floor(node.left, key);
        else if (cmp < 0) {
            Key subfloor = floor(node.right, key);
            if (subfloor != null && subfloor.compareTo(node.key) > 0)
                return subfloor;
        }
        return node.key;
    }

    private Key ceiling(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp < 0) return ceiling(node.right, key);
        else if (cmp > 0) {
            Key subCeil = ceiling(node.left, key);
            if (subCeil != null && subCeil.compareTo(node.key) < 0)
                return subCeil;
        }
        return node.key;
    }

    private int rank(Node node, Key key) {
        if (node == null) return 0;
        int cmp = node.key.compareTo(key);
        if (cmp > 0) return rank(node.left, key);
        else if (cmp < 0) return size(node.left) + rank(node.right, key) + 1;
        else return size(node.left) + 1;
    }

    private Key select(Node node, int k) {
        if (node == null) return null;
        int cmp = k - rank(node.key);
        if (cmp < 0) return select(node.left, k);
        else if (cmp > 0) return select(node.right, k);
        else return node.key;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) node = node.right;
        else node.left = deleteMin(node.left);
        if (node != null)
            node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Node deleteMax(Node node) {
        if (node.right == null) node = node.left;
        else node.right = deleteMax(node.right);
        if (node != null)
            node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Node delete(Node node, Key key) {
        int cmp = node.key.compareTo(key);
        if (cmp > 0) node.left = delete(node.left, key);
        else if (cmp < 0) node.right = delete(node.right, key);
        else {
            if (node.left == null) node = node.right;
            else if (node.right == null) node = node.left;
            else {
                Node t = node.right;
                while (t.left != null) t = t.left;
                node.key = t.key;
                node.value = t.value;
                node.right = deleteMin(node.right);
            }
        }
        if (node != null) node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    private void keys(Node node, Key lo, Key hi, Queue<Key> queue) {
        if (node == null) return;
        int cmpLo = node.key.compareTo(lo);
        int cmpHi = node.key.compareTo(hi);
        if (cmpLo > 0) keys(node.left, lo, hi, queue);
        if (cmpLo >= 0 && cmpHi <= 0) queue.enqueue(node.key);
        if (cmpHi < 0) keys(node.right, lo, hi, queue);
    }

    private void keys(Node node, Queue<Key> queue) {
        if (node == null) return;
        keys(node.left, queue);
        queue.enqueue(node.key);
        keys(node.right, queue);
    }

    private void sorted(Node node, Queue<Value> queue) {
        if (node == null) return;
        sorted(node.left, queue);
        queue.enqueue(node.value);
        sorted(node.right, queue);
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        StdOut.print("(" + node.key + "," + node.value + ") ");
        inOrder(node.right);
    }

    public static void main(String[] args) {
        StdOut.println("Test Case 1 :");
        Integer[] testCase = { 54, 5, -9, 53, 1, 0, 15 };
        Ex31BinarySearchTreeST<Integer, Integer> bstTestCase = new Ex31BinarySearchTreeST<>();
        int index = 1;
        for (int i : testCase) {
            StdOut.print("(" + i + "," + index + ") ");
            bstTestCase.put(i, index++);
        }
        StdOut.println();
        bstTestCase.inOrder();
        StdOut.println();
        StdOut.println("Size: " + bstTestCase.size() + " Min: " + bstTestCase.min() + " Max: "
                               + bstTestCase.max());
        StdOut.print("Keys : ");
        for (int i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Sorted : ");
        for (int i1 : bstTestCase.sorted()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Keys [0..20]: ");
        for (int i1 : bstTestCase.keys(0, 20)) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.println(
                "floor(10):" + bstTestCase.floor(10) + " ceil(10):" + bstTestCase.ceiling(10));
        StdOut.println(
                "floor(15):" + bstTestCase.floor(15) + " ceil(15):" + bstTestCase.ceiling(15));
        StdOut.print("Rank : ");
        for (int i : bstTestCase.keys())
            StdOut.print("(" + i + "," + bstTestCase.rank(i) + ") ");
        StdOut.println();
        StdOut.println("Select(5):" + bstTestCase.select(5));
        StdOut.print("After Deleting rank 5: ");
        bstTestCase.delete(bstTestCase.select(5));
        for (int i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting min: ");
        bstTestCase.deleteMin();
        for (int i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting max: ");
        bstTestCase.deleteMax();
        for (int i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();


        StdOut.println();
        StdOut.println("Test Case 2 :");
        Character[] testCase2 = { 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };
        Ex31BinarySearchTreeST<Character, Integer> bstTestCase2 = new Ex31BinarySearchTreeST<>();
        int i = 1;
        for (char c : testCase2) {
            StdOut.print("(" + c + "," + i + ") ");
            bstTestCase2.put(c, i++);
        }
        StdOut.println();
        bstTestCase2.inOrder();
        StdOut.println();
        StdOut.println("Size: " + bstTestCase2.size() + " Min: " + bstTestCase2.min() + " Max: "
                               + bstTestCase2.max());
        StdOut.print("Keys: ");
        for (char i1 : bstTestCase2.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Sorted: ");
        for (int i1 : bstTestCase2.sorted()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Keys [B..P]: ");
        for (char i1 : bstTestCase2.keys('B', 'P')) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.println(
                "floor(G):" + bstTestCase2.floor('G') + " ceil(G):" + bstTestCase2.ceiling('G'));
        StdOut.println(
                "floor(P):" + bstTestCase2.floor('P') + " ceil(P):" + bstTestCase2.ceiling('P'));
        StdOut.print("Rank : ");
        for (char i1 : bstTestCase2.keys())
            StdOut.print("(" + i1 + "," + bstTestCase2.rank(i1) + ") ");
        StdOut.println();
        StdOut.println("Select(5):" + bstTestCase2.select(5));
        StdOut.print("After Deleting rank 5: ");
        bstTestCase2.delete(bstTestCase2.select(5));
        for (char i1 : bstTestCase2.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting min: ");
        bstTestCase2.deleteMin();
        for (char i1 : bstTestCase2.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting max: ");
        bstTestCase2.deleteMax();
        for (char i1 : bstTestCase2.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();

    }
}
