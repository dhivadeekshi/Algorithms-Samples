/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Ex30BinarySearchInST<Key extends Comparable<Key>, Value> {
    // Use two arrays for key and value
    // Since keys are ordered use binary search to get the key-value

    private Key[] keys;
    private Value[] values;
    private int N = 0;

    public Ex30BinarySearchInST() {
        resize(1);
    }

    public void put(Key key, Value value) {
        if (size() == keys.length) resize(keys.length * 2);
        int i = getIndex(key);
        if (i == -1) {
            i = N++;
            keys[i] = key;
            values[i] = value;
            while (i > 0 && less(keys[i], keys[i - 1])) {
                exch(i, i - 1);
                if (--i == 0) break;
            }
        }
        else {
            values[i] = value;
        }
    }

    public Value get(Key key) {
        int index = getIndex(key);
        return index == -1 ? null : values[index];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public void delete(Key key) {
        int i = getIndex(key);
        if (i == -1) return;
        while (++i < N) {
            exch(i, i - 1);
        }
        keys[N] = null;
        values[N--] = null;
    }

    public boolean contains(Key key) {
        return getIndex(key) >= 0;
    }

    public Iterable<Key> keys() {
        return () -> new Iterator<Key>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < N;
            }

            @Override
            public Key next() {
                if (!hasNext()) return null;
                return keys[i++];
            }
        };
    }

    private int getIndex(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = keys[mid].compareTo(key);
            if (cmp < 0) lo = mid + 1;
            else if (cmp > 0) hi = mid - 1;
            else return mid;
        }
        return -1;
    }

    private void resize(int capacity) {
        Key[] copyKey = (Key[]) new Comparable[capacity];
        Value[] copyValue = (Value[]) new Object[capacity];

        for (int i = 0; i < N; i++) {
            copyKey[i] = keys[i];
            copyValue[i] = values[i];
        }

        keys = copyKey;
        values = copyValue;
    }

    private boolean less(Key a, Key b) {
        return a.compareTo(b) <= 0;
    }

    private void exch(int i, int j) {
        Key tempKey = keys[i];
        keys[i] = keys[j];
        keys[j] = tempKey;
        Value tempValue = values[i];
        values[i] = values[j];
        values[j] = tempValue;
    }

    public static void main(String[] args) {
        Ex30BinarySearchInST<Character, Integer> st
                = new Ex30BinarySearchInST<>();
        st.put('A', 1);
        StdOut.println("Insert A = 1");
        st.put('B', 2);
        StdOut.println("Insert B = 2");
        StdOut.print("Keys:");
        for (char x : st.keys())
            StdOut.print(x + ",");
        StdOut.println();
        StdOut.println("Value of B is " + st.get('B'));
        st.put('X', 10);
        StdOut.println("Insert X = 10");
        st.put('B', 100);
        StdOut.println("Insert B = 100");
        StdOut.print("Keys:");
        for (char x : st.keys())
            StdOut.print(x + ",");
        StdOut.println();
        StdOut.println("Value of B is " + st.get('B'));
        st.delete('B');
        StdOut.println("delete B");
        StdOut.print("Keys:");
        for (char x : st.keys())
            StdOut.print(x + ",");
        StdOut.println();
        StdOut.println("Value of B is " + st.get('B'));

    }
}
