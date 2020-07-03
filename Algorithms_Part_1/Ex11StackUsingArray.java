/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Ex11StackUsingArray<Item> {

    private Item[] items;
    private int sz = 0;
    private int minStackSize = 10;

    public Ex11StackUsingArray() {
        resize(minStackSize);
    }

    public void push(Item item) {
        if (sz == items.length) resize(items.length * 2);
        items[sz++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        Item item = items[--sz];
        items[sz] = null;
        if (sz == items.length / 4) resize(items.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    private void resize(int capacity) {
        capacity = Math.max(capacity, minStackSize);
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < sz; i++)
            copy[i] = items[i];
        items = copy;
    }

    public static void main(String[] args) {
        Ex11StackUsingArray<String> stack = new Ex11StackUsingArray<String>();
        stack.push("Hello");
        StdOut.print(stack.pop());
        stack.push("Operations");
        stack.push("Stack");
        stack.push("This");
        StdOut.print(" " + stack.pop());
        stack.push("Test");
        stack.push("To");
        stack.push("Is");

        while (!stack.isEmpty()) {
            StdOut.print(" " + stack.pop());
        }
    }
}
