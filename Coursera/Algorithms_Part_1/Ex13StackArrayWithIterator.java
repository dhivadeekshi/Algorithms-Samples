/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Ex13StackArrayWithIterator<Item> implements Iterable<Item> {

    private Item[] items = null;
    private int last = 0;

    public Ex13StackArrayWithIterator() {
        resize(1);
    }

    public void push(Item item) {
        if (size() == items.length) resize(items.length * 2);
        items[last++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is Empty");
        Item item = items[--last];
        if (size() < items.length / 4) resize(items.length / 2);
        return item;
    }

    public int size() {
        return last;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size();
            }

            @Override
            public Item next() {
                return items[current++];
            }
        };
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < last; i++)
            copy[i] = items[i];
        items = copy;
    }

    public static void main(String[] args) {
        Ex13StackArrayWithIterator<String> stack = new Ex13StackArrayWithIterator<>();
        stack.push("Hello");
        StdOut.print(stack.pop());
        stack.push("Operations");
        stack.push("Stack");
        stack.push("This");
        StdOut.print(" " + stack.pop());
        stack.push("Test");
        stack.push("To");
        stack.push("Is");

        StdOut.println();
        for (String entry : stack) {
            StdOut.print(entry + ",");
        }
        StdOut.println();

        while (!stack.isEmpty()) {
            StdOut.print(" " + stack.pop());
        }
    }
}
