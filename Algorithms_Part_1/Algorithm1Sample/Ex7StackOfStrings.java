/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex7StackOfStrings {

    private class Node {
        private Node next;
        private String value;
    }

    private Node first = null;
    private int sz = 0;

    public void push(String value) {
        Node node = new Node();
        node.next = first;
        node.value = value;
        first = node;
        sz++;
    }

    public String pop() {
        if (isEmpty()) return "";
        String value = first.value;
        first = first.next;
        sz--;
        return value;
    }

    public int size() {
        return sz;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public static void main(String[] args) {
        Ex7StackOfStrings stack = new Ex7StackOfStrings();
        stack.push("Hello");
        stack.push("World");
        StdOut.println(stack.pop() + " " + stack.pop());
    }
}
