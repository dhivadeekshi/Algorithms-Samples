/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex8QueueOfStrings {

    private class Node {
        private Node next;
        private String value;
    }

    private Node first = null;
    private Node last = null;
    private int sz = 0;

    public void enque(String value) {
        Node node = new Node();
        node.value = value;
        sz++;
        if (last == null) {
            last = node;
            first = node;
        }
        else {
            last.next = node;
        }
    }

    public String deque() {
        if (isEmpty()) return "";
        String value = first.value;
        first = first.next;
        if (first == null) last = null;
        sz--;
        return value;
    }

    public int size() {
        return sz;
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public static void main(String[] args) {

        Ex8QueueOfStrings queue = new Ex8QueueOfStrings();
        queue.enque("Hello");
        queue.enque("World");
        StdOut.println(queue.deque() + " " + queue.deque());
    }
}
