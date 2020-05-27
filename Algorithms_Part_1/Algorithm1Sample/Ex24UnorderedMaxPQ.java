/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex24UnorderedMaxPQ<Key extends Comparable<Key>> {
    // Insert elements
    // Delete max
    // isEmpty ?

    private Key[] keys;
    private int it = 0;

    public Ex24UnorderedMaxPQ() {
        resize(1);
    }

    public void insert(Key key) {
        if (size() == keys.length) resize(keys.length * 2);
        keys[it++] = key;
    }

    public Key deleteMax() {
        int keyMax = 0;
        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[keyMax]) > 0)
                keyMax = i;
        }
        Key key = keys[keyMax];
        for (int i = keyMax + 1; i < size(); i++)
            keys[i - 1] = keys[i];
        it--;
        if (size() < keys.length / 4) resize(keys.length / 2);
        return key;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int size() {
        return it;
    }

    private Key max() {
        int keyMax = 0;
        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[keyMax]) > 0)
                keyMax = i;
        }
        return keys[keyMax];
    }

    private void resize(int capacity) {
        Key[] copy = (Key[]) new Comparable[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = keys[i];
        keys = copy;
    }

    public static void main(String[] args) {
        Ex24UnorderedMaxPQ<Character> pq = new Ex24UnorderedMaxPQ<>();
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
