/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Ex29UnorderedLinkedListST<Key extends Comparable<Key>, Value> {
    // Symbol table created by maintaining an unordered linked list

    private class Node {
        private Node next;
        private Value value;
        private Key key;
    }

    private Node root = null;
    private int sz = 0;

    public void put(Key key, Value value) {
        Node node = get(root, key);
        if (node == null) {
            node = new Node();
            node.key = key;
            node.next = root;
            root = node;
        }
        node.value = value;
        sz++;
    }

    public Value get(Key key) {
        Node node = get(root, key);
        if (node == null) return null;
        return node.value;
    }

    public void delete(Key key) {
        if (isEmpty()) throw new NoSuchElementException();
        root = delete(root, key);
        sz--;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return sz;
    }

    public Iterable<Key> keys() {
        return () -> new Iterator<Key>() {
            private Node current = root;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Key next() {
                if (!hasNext()) return null;
                Key key = current.key;
                current = current.next;
                return key;
            }
        };
    }

    private Node get(Node node, Key key) {
        if (node == null) return null;
        if (node.key.compareTo(key) == 0)
            return node;
        return get(node.next, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        if (node.key.compareTo(key) == 0) return node.next;
        node.next = delete(node.next, key);
        return node;
    }

    public static void main(String[] args) {
        Ex29UnorderedLinkedListST<Character, Integer> st
                = new Ex29UnorderedLinkedListST<Character, Integer>();
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
