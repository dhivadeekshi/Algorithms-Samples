/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Ex12QueueUsingArray<Item> {

    private Item[] items;
    private int first = 0;
    private int last = 0;
    // private int minQueueSize = 10;

    public Ex12QueueUsingArray() {
        resize(1);
    }

    public void enque(Item item) {
        if (last == items.length) {
            if (size() < items.length / 2) reposition();
            else resize(items.length * 2);
        }
        items[last++] = item;
        // printStatus();
    }

    public Item deque() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = items[first++];
        if (size() < items.length / 4) resize(items.length / 2);
        // printStatus();
        return item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return last - first;
    }

    private void reposition() {
        for (int i = 0; i < size(); i++) {
            items[i] = items[i + first];
            items[i + first] = null;
        }
        last = size();
        first = 0;
    }

    private void resize(int capacity) {
        // capacity = Math.min(capacity, minQueueSize);
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = items[first + i];
        items = copy;
        last = size();
        first = 0;
    }

    private void printStatus() {
        StdOut.println("size:" + size() + " first:" + first + " last:" + last + " capacity:"
                               + items.length);
    }

    public static void main(String[] args) {
        Ex12QueueUsingArray<String> queue = new Ex12QueueUsingArray<>();
        queue.enque("Hello");
        StdOut.print(queue.deque());
        queue.enque("This");
        queue.enque("Is");
        queue.enque("To");
        StdOut.print(" " + queue.deque());
        queue.enque("Test");
        queue.enque("Queue");
        queue.enque("Operations");

        while (!queue.isEmpty()) {
            StdOut.print(" " + queue.deque());
        }
        StdOut.println();
    }
}
