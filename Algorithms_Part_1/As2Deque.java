import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
//import edu.princeton.cs.algs4.StdOut;

public class As2Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node first = null;
    private Node last = null;
    private int nItems = 0;

    // construct an empty deque
    public As2Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return nItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = null;
        first.prev = null;
        if (!isEmpty()) {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        else
            last = first;
        nItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = null;
        if (!isEmpty()) {
            oldLast.next = last;
            last.prev = oldLast;
        }
        else
            first = last;
        nItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        nItems--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else if (first != null)
            first.prev = null;
        else
            first = last;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        nItems--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        else if (last != null)
            last.next = null;
        else
            last = first;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[] testCase = { 54, 5, -9, 53, 1, 0, 15 };
        As2Deque<Integer> deque = new As2Deque<>();
        deque.addFirst(testCase[0]);
        deque.addFirst(testCase[1]);
        StdOut.printf("current size : %d\n", deque.size());
        for (Integer i : deque)
            StdOut.printf("%d,", i);
        StdOut.println();
        StdOut.println(deque.removeFirst());
        deque.addLast(testCase[2]);
        deque.addLast(testCase[3]);
        deque.addFirst(testCase[4]);
        deque.addLast(testCase[5]);
        deque.addFirst(testCase[6]);
        StdOut.printf("current size : %d\n", deque.size());
        for (Integer i : deque)
            StdOut.printf("%d,", i);
        StdOut.println();
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
    }
}
