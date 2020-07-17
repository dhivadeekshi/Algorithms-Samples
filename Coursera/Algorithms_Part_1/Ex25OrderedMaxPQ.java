/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex25OrderedMaxPQ<Key extends Comparable<Key>> {

    private Key[] keys;
    private int it = 0;

    public Ex25OrderedMaxPQ() {
        resize(1);
    }

    public void insert(Key key) {
        if (size() == keys.length) resize(keys.length * 2);
        int i = ++it;
        while (--i > 0) {
            if (keys[i - 1].compareTo(key) <= 0) break;
            keys[i] = keys[i - 1];
        }
        keys[i] = key;
    }

    public Key deleteMax() {
        Key key = keys[--it];
        keys[it] = null;
        if (size() < keys.length / 4) resize(keys.length / 2);
        return key;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private Key max() {
        if (isEmpty()) return null;
        return keys[it - 1];
    }

    private void resize(int capacity) {
        Key[] copy = (Key[]) new Comparable[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = keys[i];
        keys = copy;
    }

    private int size() {
        return it;
    }

    public static void main(String[] args) {
        Ex25OrderedMaxPQ<Character> pq = new Ex25OrderedMaxPQ<>();
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
