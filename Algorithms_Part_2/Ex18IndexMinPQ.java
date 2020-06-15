/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Ex18IndexMinPQ<Key extends Comparable<Key>> {

    private Key[] keys;
    private int[] pq, qp;
    private int sz = 0;

    public Ex18IndexMinPQ(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        pq = new int[capacity];
        qp = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            pq[i] = -1;
            qp[i] = -1;
        }
    }

    public void insert(int i, Key key) {
        keys[i] = key;
        pq[sz] = i;
        qp[i] = sz;
        swim(sz++);
    }

    public boolean contains(int i) {
        return keyOf(i) != null;
    }

    public Key keyOf(int i) {
        return keys[i];
    }

    public void changeKey(int i, Key key) {
        int cmp = key.compareTo(keys[i]);
        if (cmp < 0) decreaseKey(i, key);
        else if (cmp > 0) increaseKey(i, key);
    }

    public void decreaseKey(int i, Key key) {
        keys[i] = key;
        swim(qp[i]);
    }

    public void increaseKey(int i, Key key) {
        keys[i] = key;
        sink(qp[i]);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return sz;
    }

    public Key minKey() {
        return keys[pq[0]];
    }

    public int delMin() {
        int min = pq[0];
        exch(0, --sz);
        sink(0);
        pq[size()] = -1;
        qp[min] = -1;
        keys[min] = null;
        return min;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size(); i++)
            builder.append(keys[pq[i]]).append(" ");
        return builder.toString();
    }

    private boolean less(Key a, Key b) {
        return a.compareTo(b) <= 0;
    }

    private void exch(int i, int j) {

        int temp = qp[pq[i]];
        qp[pq[i]] = qp[pq[j]];
        qp[pq[j]] = temp;

        temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void swim(int i) {
        if (i == 0) return;
        int parent = (i + 1) / 2 - 1;
        if (less(keys[pq[i]], keys[pq[parent]])) {
            exch(i, parent);
            swim(parent);
        }
    }

    private void sink(int i) {
        int child = (i + 1) * 2 - 1;
        if (child >= size()) return;
        if (child < size() - 1 && less(keys[pq[child + 1]], keys[pq[child]])) child++;
        if (less(keys[pq[child]], keys[pq[i]])) {
            exch(i, child);
            sink(child);
        }
    }

    private void printInfo() {
        StdOut.println();
        StdOut.print("keys: ");
        for (int i = 0; i < size(); i++)
            StdOut.print(keys[i] + " ");
        StdOut.println();
        StdOut.print("pq  : ");
        for (int i = 0; i < size(); i++)
            StdOut.print(pq[i] + " ");
        StdOut.println();
        StdOut.print("qp  : ");
        for (int i = 0; i < size(); i++)
            StdOut.print(qp[i] + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        Character[] testCase = { 'S', 'O', 'R', 'T', 'I', 'N', 'G' };
        Ex18IndexMinPQ<Character> minPQ = new Ex18IndexMinPQ<>(testCase.length);
        for (int i = 0; i < testCase.length; i++) {
            minPQ.insert(i, testCase[i]);
            int rand = StdRandom.uniform(i + 1);
            StdOut.println(
                    "insert " + testCase[i] + " => min: " + minPQ.minKey() +
                            " size: " + minPQ.size() + " keyOf(" + rand + "): " + minPQ.keyOf(rand)
                            + " isEmpty? " + minPQ.isEmpty() + " -> " + minPQ);
        }

        minPQ.printInfo();

        StdOut.println();
        StdOut.print("Sorted: ");
        while (!minPQ.isEmpty()) {
            StdOut.print(minPQ.minKey() + " ");
            minPQ.delMin();
        }
        StdOut.println();

        StdOut.println();
        StdOut.println("Test decrease and increase key:");
        for (int i = 0; i < testCase.length; i++) {
            minPQ.insert(i, testCase[i]);
        }
        minPQ.printInfo();
        StdOut.println();
        Character increaseKey = 'P';
        StdOut.println("increase key 5->" + increaseKey);
        minPQ.increaseKey(5, increaseKey);
        minPQ.printInfo();
        StdOut.println();
        Character decreaseKey = 'A';
        StdOut.println("decrease key 3->" + decreaseKey);
        minPQ.decreaseKey(3, decreaseKey);
        minPQ.printInfo();
        StdOut.println();
        Character changeKey = 'J';
        StdOut.println("change key 4->" + changeKey);
        minPQ.changeKey(4, changeKey);
        minPQ.printInfo();


        StdOut.println();
        StdOut.print("Original: ");
        for (Character c : testCase)
            StdOut.print(c + " ");
        StdOut.println();
        StdOut.print("Sorted  : ");
        while (!minPQ.isEmpty()) {
            StdOut.print(minPQ.minKey() + " ");
            minPQ.delMin();
        }
        StdOut.println();
    }
}
