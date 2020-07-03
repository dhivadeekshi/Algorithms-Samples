/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Ex32TwoThreeSearchTreeST<Key extends Comparable<Key>, Value>
        implements SymbolTable<Key, Value> {
    // Initial create a two node
    // On new entry convert the two node to a three node
    // On another entry convert the three node to a four node and split them to two two nodes
    // 2 node: one key, two children
    // 3 node: two keys, three children

    private class Node {
        private Key keyLeft, keyMiddle, keyRight;
        private Value valueLeft, valueMiddle, valueRight;
        private Node nodeLeft, nodeMiddle, nodeRight, nodeMiddle1;
        private int count;
        private boolean isTwoNode, isSplitNode;

        public Node(Key key, Value value) {
            this.keyMiddle = key;
            this.valueMiddle = value;
            this.count = 1;
            isTwoNode = true;
            isSplitNode = false;
        }

        public void put(Key key, Value value) {
            if (isLeftKey(key)) valueLeft = value;
            else if (isMiddleKey(key)) valueMiddle = value;
            else if (isRightKey(key)) valueRight = value;
            else if (hasChild()) {
                if (isTwoNode) {
                    if (keyMiddle.compareTo(key) > 0) putInLeftChild(key, value);
                    else putInRightChild(key, value);
                }
                else {
                    int leftCmp = keyLeft.compareTo(key);
                    int rightCmp = keyRight.compareTo(key);
                    if (leftCmp > 0) putInLeftChild(key, value);
                    else if (rightCmp < 0) putInRightChild(key, value);
                    else putInMiddleChild(key, value);
                }
            }
            else if (isTwoNode) convertToThreeNode(key, value);
            else convertToFourNodeAndSplit(key, value);
            count = size(nodeLeft) + size(nodeMiddle) + size(nodeRight) + (isTwoNode ? 1 : 2);
        }

        private boolean compareKeys(Key key1, Key key2) {
            if (key1 == null || key2 == null) return false;
            return key1.compareTo(key2) == 0;
        }

        private boolean isLeftKey(Key key) {
            return compareKeys(keyLeft, key);
        }

        private boolean isMiddleKey(Key key) {
            return compareKeys(keyMiddle, key);
        }

        private boolean isRightKey(Key key) {
            return compareKeys(keyRight, key);
        }

        private boolean hasChild() {
            return nodeLeft != null;
        }

        private void putInLeftChild(Key key, Value value) {
            nodeLeft.put(key, value);
            if (nodeLeft.isSplitNode) {
                if (isTwoNode) moveMiddleToRight();
                else moveLeftToMiddle();
                keyLeft = nodeLeft.keyMiddle;
                valueLeft = nodeLeft.valueMiddle;
                nodeMiddle1 = nodeMiddle;
                nodeMiddle = nodeLeft.nodeRight;
                nodeLeft = nodeLeft.nodeLeft;
                if (!isTwoNode) splitFourNode();
                else isTwoNode = false;
            }
        }

        private void putInRightChild(Key key, Value value) {
            nodeRight.put(key, value);
            if (nodeRight.isSplitNode) {
                if (isTwoNode) moveMiddleToLeft();
                else moveRightToMiddle();
                keyRight = nodeRight.keyMiddle;
                valueRight = nodeRight.valueMiddle;
                if (isTwoNode) nodeMiddle = nodeRight.nodeLeft;
                else nodeMiddle1 = nodeRight.nodeLeft;
                nodeRight = nodeRight.nodeRight;
                if (!isTwoNode) splitFourNode();
                else isTwoNode = false;
            }
        }

        private void putInMiddleChild(Key key, Value value) {
            nodeMiddle.put(key, value);
            if (nodeMiddle.isSplitNode) {
                keyMiddle = nodeMiddle.keyMiddle;
                valueMiddle = nodeMiddle.valueMiddle;
                nodeMiddle1 = nodeMiddle.nodeRight;
                nodeMiddle = nodeMiddle.nodeLeft;
                if (!isTwoNode) splitFourNode();
                else isTwoNode = false;
            }
        }

        private void moveMiddleToLeft() {
            keyLeft = keyMiddle;
            valueLeft = valueMiddle;
            keyMiddle = null;
            valueMiddle = null;
        }

        private void moveMiddleToRight() {
            keyRight = keyMiddle;
            valueRight = valueMiddle;
            keyMiddle = null;
            valueMiddle = null;
        }

        private void moveLeftToMiddle() {
            keyMiddle = keyLeft;
            valueMiddle = valueLeft;
            keyLeft = null;
            valueLeft = null;
        }

        private void moveRightToMiddle() {
            keyMiddle = keyRight;
            valueMiddle = valueRight;
            keyRight = null;
            valueRight = null;
        }

        private void convertToThreeNode(Key key, Value value) {
            isTwoNode = false;
            if (keyMiddle.compareTo(key) < 0) {
                moveMiddleToLeft();
                keyRight = key;
                valueRight = value;
            }
            else {
                moveMiddleToRight();
                keyLeft = key;
                valueLeft = value;
            }
        }

        private void convertToFourNodeAndSplit(Key key, Value value) {
            Node newLeft = null, newRight = null;
            int cmpLeft = keyLeft.compareTo(key);
            int cmpRight = keyRight.compareTo(key);
            if (cmpLeft > 0) {
                moveLeftToMiddle();
                newLeft = new Node(key, value);
            }
            else if (cmpRight < 0) {
                moveRightToMiddle();
                newRight = new Node(key, value);
            }
            else {
                keyMiddle = key;
                valueMiddle = value;
            }

            if (newRight == null) {
                newRight = new Node(keyRight, valueRight);
                keyRight = null;
                valueRight = null;
            }
            if (newLeft == null) {
                newLeft = new Node(keyLeft, valueLeft);
                keyLeft = null;
                valueLeft = null;
            }

            nodeLeft = newLeft;
            nodeRight = newRight;
            isSplitNode = true;
            isTwoNode = true;
        }

        private void splitFourNode() {
            isSplitNode = true;
            isTwoNode = true;
            Node newLeft = new Node(keyLeft, valueLeft);
            Node newRight = new Node(keyRight, valueRight);

            newLeft.nodeLeft = nodeLeft;
            newLeft.nodeRight = nodeMiddle;

            newRight.nodeLeft = nodeMiddle1;
            newRight.nodeRight = nodeRight;

            newLeft.count = size(newLeft.nodeLeft) + size(newLeft.nodeRight) + 1;
            newRight.count = size(newRight.nodeLeft) + size(newRight.nodeRight) + 1;

            nodeRight = newRight;
            nodeLeft = newLeft;
            nodeMiddle = null;
            nodeMiddle1 = null;

            keyLeft = null;
            valueLeft = null;
            keyRight = null;
            valueRight = null;
        }
    }

    private Node root = null;

    @Override
    public void put(Key key, Value value) {
        if (isEmpty()) root = new Node(key, value);
        else root.put(key, value);
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

    private Value get(Node node, Key key) {
        if (node == null) return null;
        if (node.isTwoNode) {
            int cmp = node.keyMiddle.compareTo(key);
            if (cmp < 0) return get(node.nodeRight, key);
            else if (cmp > 0) return get(node.nodeLeft, key);
            else return node.valueMiddle;
        }
        else {
            int leftCmp = node.keyLeft.compareTo(key);
            int rightCmp = node.keyRight.compareTo(key);
            if (leftCmp == 0) return node.valueLeft;
            else if (rightCmp == 0) return node.valueRight;
            else if (leftCmp > 0) return get(node.nodeLeft, key);
            else if (rightCmp < 0) return get(node.nodeRight, key);
            else return get(node.nodeMiddle, key);
        }
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    private Key min(Node node) {
        if (node == null) return null;
        if (node.nodeLeft != null) return min(node.nodeLeft);
        return node.isTwoNode ? node.keyMiddle : node.keyLeft;
    }

    private Key max(Node node) {
        if (node == null) return null;
        if (node.nodeRight != null) return max(node.nodeRight);
        return node.isTwoNode ? node.keyMiddle : node.keyRight;
    }

    private Key floor(Node node, Key key) {
        if (node == null) return null;
        if (node.isTwoNode) {
            int cmp = node.keyMiddle.compareTo(key);
            if (cmp > 0) return floor(node.nodeLeft, key);
            else if (cmp == 0) return node.keyMiddle;
            else {
                Key subFloor = floor(node.nodeRight, key);
                if (subFloor != null && subFloor.compareTo(node.keyMiddle) > 0)
                    return subFloor;
                return node.keyMiddle;
            }
        }
        else {
            int leftCmp = node.keyLeft.compareTo(key);
            int rightCmp = node.keyRight.compareTo(key);
            if (leftCmp == 0 || rightCmp == 0) return key;
            else if (leftCmp > 0) return floor(node.nodeLeft, key);
            else if (rightCmp < 0) {
                Key subFloor = floor(node.nodeRight, key);
                if (subFloor != null && subFloor.compareTo(node.keyRight) > 0)
                    return subFloor;
                return node.keyRight;
            }
            else {
                Key subFloor = floor(node.nodeMiddle, key);
                if (subFloor != null && subFloor.compareTo(node.keyLeft) > 0)
                    return subFloor;
                return node.keyLeft;
            }
        }
    }

    private Key ceiling(Node node, Key key) {
        if (node == null) return null;

        if (node.isTwoNode) {
            int cmp = node.keyMiddle.compareTo(key);
            if (cmp == 0) return key;
            else if (cmp < 0) return ceiling(node.nodeRight, key);
            else {
                Key subCeil = ceiling(node.nodeLeft, key);
                if (subCeil != null && subCeil.compareTo(node.keyMiddle) < 0) return subCeil;
                return node.keyMiddle;
            }
        }
        else {
            int leftCmp = node.keyLeft.compareTo(key);
            int rightCmp = node.keyRight.compareTo(key);
            if (leftCmp == 0 || rightCmp == 0) return key;
            else if (rightCmp < 0) return ceiling(node.nodeRight, key);
            else if (leftCmp > 0) {
                Key subCeil = ceiling(node.nodeLeft, key);
                if (subCeil != null && subCeil.compareTo(node.keyLeft) < 0)
                    return subCeil;
                return node.keyLeft;
            }
            else {
                Key subCeil = ceiling(node.nodeMiddle, key);
                if (subCeil != null && subCeil.compareTo(node.keyRight) < 0)
                    return subCeil;
                return node.keyRight;
            }
        }
    }

    private int rank(Node node, Key key) {
        if (node == null) return 0;
        if (node.isTwoNode) {
            int cmp = node.keyMiddle.compareTo(key);
            if (cmp > 0) return rank(node.nodeLeft, key);
            else if (cmp < 0) return size(node.nodeLeft) + rank(node.nodeRight, key) + 1;
            else return size(node.nodeLeft) + 1;
        }
        else {
            int leftCmp = node.keyLeft.compareTo(key);
            int rightCmp = node.keyRight.compareTo(key);
            if (leftCmp == 0) return size(node.nodeLeft) + 1;
            else if (rightCmp == 0) return size(node.nodeLeft) + size(node.nodeMiddle) + 2;
            else if (leftCmp > 0) return rank(node.nodeLeft, key);
            else if (rightCmp > 0) return rank(node.nodeMiddle, key);
            else return size(node.nodeLeft) + 1 + rank(node.nodeMiddle, key);
        }
    }

    private Key select(Node node, int k) {
        if (node == null) return null;
        if (node.isTwoNode) {
            int cmp = k - rank(node.keyMiddle);
            if (cmp < 0) return select(node.nodeLeft, k);
            else if (cmp > 0) return select(node.nodeRight, k);
            else return node.keyMiddle;
        }
        else {
            int leftCmp = k - rank(node.keyLeft);
            int rightCmp = k - rank(node.keyRight);
            if (leftCmp == 0) return node.keyLeft;
            else if (rightCmp == 0) return node.keyRight;
            else if (leftCmp < 0) return select(node.nodeLeft, k);
            else if (rightCmp > 0) return select(node.nodeRight, k);
            else return select(node.nodeMiddle, k);
        }
    }

    private Node deleteMin(Node node) {
        return node; // TODO
    }

    private Node deleteMax(Node node) {
        return node; // TODO
    }

    private Node delete(Node node, Key key) {
        return node; // TODO
    }

    private boolean isKeyInRange(Key key, Key lo, Key hi) {
        return key.compareTo(lo) >= 0 && key.compareTo(hi) <= 0;
    }

    private void keys(Node node, Key lo, Key hi, Queue<Key> queue) {
        if (node == null) return;
        keys(node.nodeLeft, lo, hi, queue);
        if (node.isTwoNode) {
            if (isKeyInRange(node.keyMiddle, lo, hi)) queue.enqueue(node.keyMiddle);
        }
        else {
            if (isKeyInRange(node.keyLeft, lo, hi)) queue.enqueue(node.keyLeft);
            keys(node.nodeMiddle, lo, hi, queue);
            if (isKeyInRange(node.keyRight, lo, hi)) queue.enqueue(node.keyRight);
        }
        keys(node.nodeRight, lo, hi, queue);
    }

    private void keys(Node node, Queue<Key> queue) {
        if (node == null) return;
        keys(node.nodeLeft, queue);
        if (node.isTwoNode)
            queue.enqueue(node.keyMiddle);
        else {
            queue.enqueue(node.keyLeft);
            keys(node.nodeMiddle, queue);
            queue.enqueue(node.keyRight);
        }
        keys(node.nodeRight, queue);
    }

    private void sorted(Node node, Queue<Value> queue) {
        if (node == null) return;
        sorted(node.nodeLeft, queue);
        if (node.isTwoNode)
            queue.enqueue(node.valueMiddle);
        else {
            queue.enqueue(node.valueLeft);
            sorted(node.nodeMiddle, queue);
            queue.enqueue(node.valueRight);
        }
        sorted(node.nodeRight, queue);
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.nodeLeft);
        if (node.isTwoNode) {
            StdOut.print("(" + node.keyMiddle + "," + node.valueMiddle + ",2) ");
        }
        else {

            StdOut.print("(" + node.keyLeft + "," + node.valueLeft + ",3) ");
            inOrder(node.nodeMiddle);
            StdOut.print("(" + node.keyRight + "," + node.valueRight + ",3) ");

        }
        inOrder(node.nodeRight);
    }

    public static <Key extends Comparable<Key>> void test(Key[] keys, Key lo, Key hi, Key floor1,
                                                          Key floor2) {

        Ex32TwoThreeSearchTreeST<Key, Integer> bstTestCase
                = new Ex32TwoThreeSearchTreeST<>();
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
