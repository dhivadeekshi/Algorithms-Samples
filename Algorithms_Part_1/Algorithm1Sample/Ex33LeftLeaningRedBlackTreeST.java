/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex33LeftLeaningRedBlackTreeST<Key extends Comparable<Key>, Value>
        implements SymbolTable<Key, Value> {
    // Trying to imitate a BST as a 2-3 tree using red black links
    // Two node is black link, three node is a red link

    private boolean RED = true;
    private boolean BLACK = false;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int count;
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
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
        return min(root);
    }

    @Override
    public Key max() {
        return max(root);
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
        root = deleteMin(root);
    }

    @Override
    public void deleteMax() {
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
        Queue<Value> queue = new Queue<>();
        sorted(root, queue);
        return queue;
    }

    public void inOrder() {
        inOrder(root);
    }

    private void updateCount(Node node) {
        if (node == null) return;
        node.count = size(node.left) + size(node.right) + 1;
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        node.color = RED;
        updateCount(node);
        return right;
    }

    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        left.color = node.color;
        node.color = RED;
        updateCount(node);
        return left;
    }

    private void flipColors(Node node) {
        node.left.color = BLACK;
        node.right.color = BLACK;
        node.color = RED;
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) node = new Node(key, value, RED);
        int cmp = node.key.compareTo(key);
        if (cmp > 0) node.left = put(node.left, key, value);
        else if (cmp < 0) node.right = put(node.right, key, value);
        else node.value = value;

        if (!isRed(node.left) && isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        updateCount(node);
        return node;
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp > 0) return get(node.left, key);
        else if (cmp < 0) return get(node.right, key);
        else return node.value;
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    private Key min(Node node) {
        if (node == null) return null;
        if (node.left != null) return min(node.left);
        return node.key;
    }

    private Key max(Node node) {
        if (node == null) return null;
        if (node.right != null) return max(node.right);
        return node.key;
    }

    private Key floor(Node node, Key key) {
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if (cmp > 0) return floor(node.left, key);
        else if (cmp < 0) {
            Key subFloor = floor(node.right, key);
            if (subFloor != null && subFloor.compareTo(node.key) > 0)
                return subFloor;
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
        if (cmp > 0) return select(node.right, k);
        else if (cmp < 0) return select(node.left, k);
        else return node.key;
    }

    private Node deleteMin(Node node) {
        if (node == null) return null;
        if (node.left == null) return null;
        node.left = deleteMin(node.left);
        node.count = size(node.left) + size(node.right) + 1;
        return node; // TODO update properly
    }

    private Node deleteMax(Node node) {
        if (node == null) return null;
        if (node.right == null) return null;
        node.right = deleteMax(node.right);
        node.count = size(node.left) + size(node.right) + 1;
        return node; // TODO update properly
    }

    private Node delete(Node node, Key key) {
        return node; // TODO
    }

    private boolean isKeyInRange(Key key, Key lo, Key hi) {
        return key.compareTo(lo) >= 0 && key.compareTo(hi) <= 0;
    }

    private void keys(Node node, Key lo, Key hi, Queue<Key> queue) {
        if (node == null) return;
        keys(node.left, lo, hi, queue);
        if (isKeyInRange(node.key, lo, hi)) queue.enqueue(node.key);
        keys(node.right, lo, hi, queue);
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


    public static <Key extends Comparable<Key>> void test(Key[] keys, Key lo, Key hi, Key floor1,
                                                          Key floor2) {

        Ex33LeftLeaningRedBlackTreeST<Key, Integer> bstTestCase
                = new Ex33LeftLeaningRedBlackTreeST<>();
        int index = 1;
        for (Key i : keys) {
            StdOut.print("(" + i + "," + index + ") ");
            bstTestCase.put(i, index++);
        }
        StdOut.println();
        bstTestCase.inOrder();
        StdOut.println();
        StdOut.println("Size: " + bstTestCase.size() + " Min: " + bstTestCase.min() + " Max: "
                               + bstTestCase.max());
        StdOut.print("Keys : ");
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Sorted : ");
        for (int i1 : bstTestCase.sorted()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("Keys [" + lo + ".." + hi + "]: ");
        for (Key i1 : bstTestCase.keys(lo, hi)) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.println(
                "floor(" + floor1 + "):" + bstTestCase.floor(floor1) + " ceil(" + floor1 + "):"
                        + bstTestCase.ceiling(floor1));
        StdOut.println(
                "floor(" + floor2 + "):" + bstTestCase.floor(floor2) + " ceil(" + floor2 + "):"
                        + bstTestCase.ceiling(floor2));
        StdOut.print("Rank : ");
        for (Key i : bstTestCase.keys())
            StdOut.print("(" + i + "," + bstTestCase.rank(i) + ") ");
        StdOut.println();
        StdOut.println("Select(5):" + bstTestCase.select(5));
        StdOut.print("After Deleting rank 5: ");
        bstTestCase.delete(bstTestCase.select(5));
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting min: ");
        bstTestCase.deleteMin();
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting max: ");
        bstTestCase.deleteMax();
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        StdOut.println("Test Case 1 :");
        Integer[] testCase = { 54, 5, -9, 53, 1, 0, 15 };
        test(testCase, 0, 20, 10, 15);

        StdOut.println();
        StdOut.println("Test Case 2 :");
        Character[] testCase2 = { 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };
        test(testCase2, 'B', 'P', 'G', 'P');
    }
}
