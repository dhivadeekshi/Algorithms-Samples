/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex26BinaryHeapPQ<Key extends Comparable<Key>> {
    // Array representation of a heap-ordered complete binary tree
    // Keys in nodes
    // Parent's keys no smaller than children's keys
    // Largest key is the a[1], which is the root of binary tree
    // Can use array indices to move through tree
    //      Parent of node k is k/2.
    //      Children of node k is 2k & 2k + 1
    // Insert: Put a new node at the end and swim up to eliminate violation
    // Replace: Sink down to the bottom to eliminate violation
    // Delete max: exchange with the last node and null it and sink the root node down
    private Key[] keys;
    private int sz = 0;

    public Ex26BinaryHeapPQ(int capacity) {
        keys = (Key[]) new Comparable[capacity + 1];
    }

    public void insert(Key key) {
        keys[++sz] = key;
        swim(sz);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Key deleteMax() {
        exch(1, sz);
        Key key = keys[sz];
        keys[sz--] = null;
        sink(1);
        return key;
    }

    private int size() {
        return sz;
    }

    private Key max() {
        return keys[1];
    }

    private boolean less(Key a, Key b) {
        return a.compareTo(b) <= 0;
    }

    private void sink(int i) {
        if (i * 2 > size()) return;
        int child = 2 * i;
        if (child + 1 <= size() && less(keys[child], keys[child + 1])) child++;
        if (less(keys[i], keys[child])) {
            exch(i, child);
            sink(child);
        }
    }

    private void swim(int i) {
        if (i <= 1) return;
        int parent = i / 2;
        if (less(keys[parent], keys[i])) {
            exch(parent, i);
            swim(parent);
        }
    }

    private void exch(int i, int j) {
        Key temp = keys[i];
        keys[i] = keys[j];
        keys[j] = temp;
    }

    public static void main(String[] args) {
        Ex26BinaryHeapPQ<Character> pq = new Ex26BinaryHeapPQ<>(10);
        pq.insert('P');
        StdOut.println("insert:P max:" + pq.max());
        pq.insert('Q');
        StdOut.println("insert:Q max:" + pq.max());
        pq.insert('E');
        StdOut.println("insert:E max:" + pq.max());
        StdOut.println("delete Max : " + pq.deleteMax() + " expected:Q");
        pq.insert('X');
        StdOut.println("insert:X max:" + pq.max());
        pq.insert('A');
        StdOut.println("insert:A max:" + pq.max());
        pq.insert('M');
        StdOut.println("insert:M max:" + pq.max());
        StdOut.println("delete Max : " + pq.deleteMax() + " expected:X");
        pq.insert('P');
        StdOut.println("insert:P max:" + pq.max());
        pq.insert('L');
        StdOut.println("insert:L max:" + pq.max());
        pq.insert('E');
        StdOut.println("insert:E max:" + pq.max());
        StdOut.println("delete Max : " + pq.deleteMax() + " expected:P");

    }
}
