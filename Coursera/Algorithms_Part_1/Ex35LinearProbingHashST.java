/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex35LinearProbingHashST<Key, Value> {

    private static final int M = 1000;
    private Key[] keys = (Key[]) new Object[M];
    private Value[] values = (Value[]) new Object[M];
    private int sz = 0;

    public void put(Key key, Value value) {
        int i = hash(key), tombStone = -1;
        while (keys[i] != null && !keys[i].equals(key)) {
            if (tombStone == -1 && values[i] == null) tombStone = i;
            i = nextIndex(i);
        }
        if (keys[i] == null) {
            if (tombStone != -1) i = tombStone;
            keys[i] = key;
            values[i] = value;
            sz++;
        }
        else values[i] = value;
    }

    public Value get(Key key) {
        int i = hash(key);
        while (keys[i] != null && !keys[i].equals(key)) i = nextIndex(i);
        if (keys[i] == null) return null;
        return values[i];
    }

    public void delete(Key key) {
        int i = hash(key);
        while (keys[i] != null && !keys[i].equals(key)) i = nextIndex(i);
        if (keys[i] == null) return;
        values[i] = null;
        sz--;
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
        for (int i = 0; i < M; i++) {
            if (keys[i] != null && values[i] != null) queue.enqueue(keys[i]);
        }
        return queue;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private int nextIndex(int i) {
        return (i + 1) % M;
    }

    public static <Key extends Comparable<Key>> void test(Key[] keys, Key lo, Key hi, Key floor1,
                                                          Key floor2) {

        Ex35LinearProbingHashST<Key, Integer> bstTestCase
                = new Ex35LinearProbingHashST<>();
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
        StdOut.print("After Deleting " + lo + ": ");
        bstTestCase.delete(lo);
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
        StdOut.print("After Deleting " + hi + ": ");
        bstTestCase.delete(hi);
        for (Key i1 : bstTestCase.keys()) {
            StdOut.print(i1 + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        StdOut.println("Test Case 1 :");
        Integer[] testCase = { 54, 5, -9, 53, 1, 0, 15, -9 };
        test(testCase, 0, 20, -9, 15);

        StdOut.println();
        StdOut.println("Test Case 2 :");
        Character[] testCase2 = { 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E' };
        test(testCase2, 'B', 'P', 'E', 'P');
    }
}
