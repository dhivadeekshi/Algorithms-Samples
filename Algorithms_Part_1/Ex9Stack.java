/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Ex9Stack<Item> {

    private class Node {
        private Item item;
        private Node next;
    }

    private Node first = null;
    private int sz = 0;

    public void push(Item item) {
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
        sz++;
    }

    public Item pop() {
        if (isEmpty()) return null;
        Item item = first.item;
        first = first.next;
        sz--;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return sz;
    }

    public static void main(String[] args) {

    }
}
