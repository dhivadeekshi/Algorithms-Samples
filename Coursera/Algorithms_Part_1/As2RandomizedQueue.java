import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
/*
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
 */

public class As2RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int nItems = 0;

    // construct an empty randomized queue
    public As2RandomizedQueue() {
        resize(1);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return nItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (nItems == items.length) resize(2 * items.length);
        items[nItems++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = index();
        Item item = items[i];
        items[i] = items[--nItems];
        items[nItems] = null;
        if (nItems > 1 && nItems <= items.length / 4) resize(items.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[index()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < nItems; i++)
            copy[i] = items[i];
        items = copy;
    }

    private int index() {
        if (isEmpty()) return -1;
        return StdRandom.uniform(nItems);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices;
        private int current = 0;

        public RandomizedQueueIterator() {
            indices = new int[nItems];
            for (int i = 0; i < nItems; i++)
                indices[i] = i;
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return current < nItems;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[indices[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[] testCase = { 54, 5, -9, 53, 1, 0, 15 };
        As2RandomizedQueue<Integer> deque = new As2RandomizedQueue<>();
        deque.enqueue(testCase[0]);
        deque.enqueue(testCase[1]);
        StdOut.printf("current size : %d\n", deque.size());
        for (Integer i : deque)
            StdOut.printf("%d,", i);
        StdOut.println();
        StdOut.println(deque.dequeue());
        deque.enqueue(testCase[2]);
        deque.enqueue(testCase[3]);
        deque.enqueue(testCase[4]);
        deque.enqueue(testCase[5]);
        deque.enqueue(testCase[6]);
        StdOut.printf("current size : %d\n", deque.size());
        for (Integer i : deque)
            StdOut.printf("%d,", i);
        StdOut.println();
        StdOut.println(deque.dequeue());
        StdOut.println(deque.dequeue());
        StdOut.println(deque.dequeue());
        StdOut.println(deque.dequeue());
        StdOut.println(deque.dequeue());
        StdOut.println(deque.dequeue());
    }
}
