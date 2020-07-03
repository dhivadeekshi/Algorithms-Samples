/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex34SeparateChainingHashST<Key, Value> {

    private static class Node {
        private Object key;
        private Object value;
        private Node next;

        public Node(Object key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int M = 10;
    private Node[] items = new Node[M];
    private int sz = 0;

    public void put(Key key, Value value) {
        int i = hash(key);
        Node node = items[i];
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        items[i] = new Node(key, value, items[i]);
        sz++;
    }

    public Value get(Key key) {
        int i = hash(key);
        Node node = items[i];
        while (node != null && !node.key.equals(key)) node = node.next;
        return node == null ? null : (Value) node.value;
    }

    public void delete(Key key) {
        int i = hash(key);
        items[i] = delete(items[i], key);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return sz;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++)
            keys(items[i], queue);
        return queue;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        if (node.key.equals(key)) {
            sz--;
            return node.next;
        }
        node.next = delete(node.next, key);
        return node;
    }

    private void keys(Node node, Queue<Key> queue) {
        if (node == null) return;
        queue.enqueue((Key) node.key);
        keys(node.next, queue);
    }

    public static <Key extends Comparable<Key>> void test(Key[] keys, Key lo, Key hi, Key floor1,
                                                          Key floor2) {

        Ex34SeparateChainingHashST<Key, Integer> bstTestCase = new Ex34SeparateChainingHashST<>();
        int index = 1;
        for (Key i : keys) {
            StdOut.print("(" + i + "," + index + ") ");
            bstTestCase.put(i, index++);
        }
        StdOut.println();
        StdOut.println("Size: " + bstTestCase.size());
        StdOut.print("Keys : ");
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.println("get " + lo + ":" + bstTestCase.get(lo));
        StdOut.println("get " + hi + ":" + bstTestCase.get(hi));
        StdOut.println("get " + floor1 + ":" + bstTestCase.get(floor1));
        StdOut.println("get " + floor2 + ":" + bstTestCase.get(floor2));
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
