/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Ex10Queue<Item> {

    private class Node {
        private Item item;
        private Node next;
    }

    private Node first = null;
    private Node last = null;
    private int sz = 0;

    public void enque(Item item) {
        Node node = new Node();
        node.item = item;
        if (last == null) {
            last = node;
            first = node;
        }
        else {
            last.next = node;
        }
        sz++;
    }

    public Item deque() {
        if (isEmpty()) return null;
        Item item = first.item;
        first = first.next;
        if (first == null) last = null;
        sz--;
        return item;
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public int size() {
        return sz;
    }

    public static void main(String[] args) {

    }
}
